package com.github.yanzord;

import com.github.yanzord.kafka.*;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@KafkaIntegrationTest
public class DummyProducerTest {

    @Autowired
    private DummyProducer producer;

    @Autowired
    private ConsumerMockFactory consumerMockFactory;

    private ConsumerMock consumerMock;

    @Test
    void deveProduzirMensagem(){
        consumerMock = consumerMockFactory.forTopic("dummy-topic-v1");

        Dummy dto = new Dummy();
        dto.setId("xyz");

        producer.sendMessage(dto);

        String message = consumerMock.getMessage().getPayload();

        ReadContext json = JsonPath.parse(message);

        assertThat(json.read("id"), is(dto.getId()));
    }
}
