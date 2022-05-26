package com.github.yanzord.kafka;

import org.springframework.context.annotation.Bean;

public class KafkaTestConfig {

    @Bean
    public ConsumerMockFactory consumerDummyFactory() {
        return new ConsumerMockFactory();
    }

    @Bean
    public ProducerMockFactory producerMockFactory() {
        return new ProducerMockFactory();
    }

}
