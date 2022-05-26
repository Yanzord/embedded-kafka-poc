package com.github.yanzord;

import com.github.yanzord.kafka.KafkaIntegrationTest;
import com.github.yanzord.kafka.ProducerMock;
import com.github.yanzord.kafka.ProducerMockFactory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@KafkaIntegrationTest
public class DummyConsumerTest {

    @SpyBean
    private DummyConsumer consumer;

    @Autowired
    private ProducerMockFactory producerMockFactory;

    private ProducerMock producerMock;

    @Captor
    ArgumentCaptor<Dummy> dummyArgumentCaptor;

    @Test
    void deveConsumirMensagem() {

        producerMock = producerMockFactory.forTopic("dummy-topic-v1");

        String id = "xyz";
        String payload = createPayload(id);

        producerMock.send(payload);

        verify(consumer, timeout(5000)).receiveMessage(dummyArgumentCaptor.capture());

        Dummy dummy = dummyArgumentCaptor.getValue();

        assertThat(dummy.getId(), is(id));
    }

    private String createPayload(String id) {
        return "{" +
                "	\"id\": \"" + id + "\"" +
                "}";
    }
}
