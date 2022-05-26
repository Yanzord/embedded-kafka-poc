package com.github.yanzord.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TestComponent
public class ProducerMockFactory {

    private Map<String, ProducerMock> messagesByTopic;

    public ProducerMockFactory() {
        this.messagesByTopic = new ConcurrentHashMap<>();
    }

    public ProducerMock forTopic(String topic) {
        return messagesByTopic.computeIfAbsent(topic, key -> new ProducerMock(topic, producerProperties()));
    }

    public static Map<String, Object> producerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaIntegrationTest.BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }

}
