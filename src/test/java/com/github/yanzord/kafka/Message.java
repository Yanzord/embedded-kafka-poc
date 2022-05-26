package com.github.yanzord.kafka;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Message {

    private final String payload;

    public Message(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("payload", payload)
                .toString();
    }
}
