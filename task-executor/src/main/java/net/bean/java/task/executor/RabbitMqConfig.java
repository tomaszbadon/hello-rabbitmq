package net.bean.java.task.executor;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public final static String ACTIVE_TASK_QUEUE = "active-task";

    public final static String INCOMING_TASK_QUEUE = "incoming";

    @Bean
    Queue queue() {
        return new Queue(ACTIVE_TASK_QUEUE, false);
    }

}
