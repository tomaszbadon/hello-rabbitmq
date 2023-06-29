package net.bean.java.task.executor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Slf4j
public class Task implements Runnable {

    @JsonIgnore
    private final String id;

    @JsonIgnore
    private final AtomicLong progress;

    @JsonIgnore
    private final RabbitTemplate template;

    @JsonIgnore
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run() {
        try {
            Random random = new Random();
            final int pause = random.nextInt(100) + 100;
            for(int i = 0 ; i < 100 ; i++) {
                Thread.sleep(pause);
                progress.set(Math.round(((i +1) / 100.0) * 100));
                sendNotification();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sendNotification() {
        try {
            String xml = mapper.writeValueAsString(this);
            template.convertAndSend(RabbitMqConfig.ACTIVE_TASK_QUEUE, xml);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public long getProgress() {
        return progress.get();
    }
}
