package net.bean.java.task.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageScheduler {

    @Autowired
    private RabbitTemplate template;

    private final ObjectMapper mapper = new ObjectMapper();

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        try {
            log.info("Message was sent");
            String xml = mapper.writeValueAsString(new Message("2023-06-26", "incoming", "Ble ble Ble"));
            template.convertAndSend(RabbitMqConfig.INCOMING_TASK_QUEUE, xml);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        } catch (AmqpException e) {
            log.error(e.getMessage(), e);
        }
    }

}

record Message(String date, String type, String content) { }
