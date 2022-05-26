package com.github.yanzord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(DummyBinders.class)
public class DummyProducer {

    private final DummyBinders channel;

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyProducer.class);

    public DummyProducer(DummyBinders channel) {
        this.channel = channel;
    }

    public Dummy sendMessage(Dummy dummy) {
        channel.dummyEventOutputChannel().send(new GenericMessage<>(dummy));
        LOGGER.info("Evento dummy enviado: {}", dummy);
        return dummy;
    }
}
