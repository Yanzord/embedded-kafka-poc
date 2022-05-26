package com.github.yanzord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DummyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyConsumer.class);
    private static final String TOPIC = "dummy-topic-v1";

    @KafkaListener(groupId = "dummyConsumer", topics = TOPIC)
    public void receiveMessage(Dummy dummy) {
        LOGGER.info("Evento dummy recebido: {}", dummy);
    }

}
