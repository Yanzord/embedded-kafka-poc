package com.github.yanzord;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DummyBinders {

    String RECEIVE_DUMMY_EVENT = "receiveDummyEvent";
    String SEND_DUMMY_EVENT = "sendDummyEvent";

    @Input(RECEIVE_DUMMY_EVENT)
    SubscribableChannel dummyEventInputChannel();

    @Output(SEND_DUMMY_EVENT)
    MessageChannel dummyEventOutputChannel();
}
