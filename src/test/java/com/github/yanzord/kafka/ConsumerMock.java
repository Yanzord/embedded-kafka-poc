package com.github.yanzord.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ConsumerMock {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerMock.class);

    private static final int POOL_INTERVAL = 500;

    private final String topic;
    private final MessageCollector messageCollector;
    private final KafkaConsumer<String, String> consumer;
    private final ExecutorService executor;

    public ConsumerMock(String topic, Properties consumerProperties) {
        this.topic = topic;
        this.consumer = new KafkaConsumer<>(consumerProperties);
        this.messageCollector = new BlockingHeadMessageCollector(topic, new QueueMessageCollector());
        this.executor = Executors.newSingleThreadExecutor(new NamedThreadFactory(topic + "-consumer-mock"));
    }

    public ConsumerMock subscribe() {
        executor.submit(() -> {
            consumer.subscribe(List.of(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                records.forEach(this::put);

                LOGGER.debug("{} mensagens consumidas, aguardando {} ms até próxima verificação de mensagens publicadas no tópico '{}'...", records.count(), POOL_INTERVAL, topic);
                ConsumerMockFactory.sleep(POOL_INTERVAL);
            }
        });

        return this;
    }

    public Message getMessage() {
        Message head = messageCollector.getHead();

        if (head == null)
            throw new AssertionError("Nehuma mensagem consumida no tópico '" + topic + "'");

        return head;
    }

    private Message convert(ConsumerRecord<?, ?> head) {
        return new Message(readPayload(head));
    }

    private void put(ConsumerRecord<?, ?> record) {
        LOGGER.info("Mensagem no tópico '{}' consumida: Payload: {} Headers: {}", topic, record.value(), record.headers());
        messageCollector.put(convert(record));
    }

    private String readPayload(ConsumerRecord<?, ?> head) {
        Object value = head.value();
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof byte[]) {
            return new String((byte[]) value);
        } else {
            throw new RuntimeException("Não foi possível converter para String o payload do tipo '" + value.getClass() + "'");
        }
    }

}
