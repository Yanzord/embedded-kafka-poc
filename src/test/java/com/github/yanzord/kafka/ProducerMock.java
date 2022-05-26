package com.github.yanzord.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.Map;

public class ProducerMock {

    private final String topic;
    private final Producer<String, String> producer;

    public ProducerMock(String topic, Map<String, Object> properties) {
        this.topic = topic;
        this.producer = new DefaultKafkaProducerFactory<String, String>(properties).createProducer();
    }

    public void send(String value) {
        producer.send(new ProducerRecord<>(topic, value));
        producer.flush();
    }
}
