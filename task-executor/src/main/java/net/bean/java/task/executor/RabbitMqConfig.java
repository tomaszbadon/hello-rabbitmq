package net.bean.java.task.executor;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public final static String QUEUE_NAME = "active-task";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

}
