package com.github.yanzord.kafka;

import com.github.yanzord.Application;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Tag("slow")
@DirtiesContext
@ActiveProfiles({"kafka", "test"})
@SpringBootTest(classes = { Application.class, KafkaTestConfig.class })
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://" + KafkaIntegrationTest.BOOTSTRAP_SERVERS, "port=9093" })
@ExtendWith(SpringExtension.class)
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface KafkaIntegrationTest {

    String BOOTSTRAP_SERVERS = "localhost:9093";

    @AliasFor(attribute = "properties", annotation = SpringBootTest.class)
    String[] properties() default {};

    @AliasFor(attribute = "partitions", annotation = EmbeddedKafka.class)
    int partitions() default 1;

}
