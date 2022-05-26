package com.github.yanzord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(DummyBinders.class)
public class DummyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyConsumer.class);

    @StreamListener(DummyBinders.RECEIVE_DUMMY_EVENT)
    public void receiveMessage(Dummy dummy) {
        LOGGER.info("Evento dummy recebido: {}", dummy);
    }

}
