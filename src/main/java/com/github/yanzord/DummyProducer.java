package com.github.yanzord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class DummyProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyProducer.class);
    private static final String TOPIC = "dummy-topic-v1";

    private final KafkaTemplate<String, Object> template;

    public DummyProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public Dummy sendMessage(Dummy dummy) {
        Message<?> message = MessageBuilder.withPayload(dummy)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();

        template.send(message);

        LOGGER.info("Evento dummy enviado: {}", dummy);
        return dummy;
    }
}
