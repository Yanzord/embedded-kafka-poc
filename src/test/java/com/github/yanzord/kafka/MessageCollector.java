package com.github.yanzord.kafka;

public interface MessageCollector {

    void put(Message message);

    Message getHead();

    default void clear() {}

}
