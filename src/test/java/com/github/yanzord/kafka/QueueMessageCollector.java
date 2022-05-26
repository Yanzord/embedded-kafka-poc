package com.github.yanzord.kafka;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueMessageCollector implements MessageCollector {

    private final Queue<Message> messages;

    public QueueMessageCollector() {
        this(new LinkedBlockingQueue<>());
    }

    public QueueMessageCollector(Queue<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void put(Message message) {
        messages.add(message);
    }

    @Override
    public Message getHead() {
        return messages.poll();
    }

    @Override
    public void clear() {
        messages.clear();
    }
}
