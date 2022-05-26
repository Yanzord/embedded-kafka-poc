package com.github.yanzord.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlockingHeadMessageCollector implements MessageCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockingHeadMessageCollector.class);

    private final String topic;
    private final MessageCollector delegate;

    BlockingHeadMessageCollector(String topic, MessageCollector delegate) {
        this.topic = topic;
        this.delegate = delegate;
    }

    @Override
    public void put(Message message) {
        delegate.put(message);
    }

    @Override
    public Message getHead() {
        CompletableFuture<Message> future = new CompletableFuture<>();

        future.completeAsync(() -> {
            Message head;

            while ((head = delegate.getHead()) == null) {
                LOGGER.debug("Aguardando {} ms por consumo da mensagem no tópico '{}'...", 50, topic);
                ConsumerMockFactory.sleep(50);
            }

            return head;
        });

        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new AssertionError("Nenhuma mensagem consumida no tópico '" + topic + "'");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        delegate.clear();
    }
}
