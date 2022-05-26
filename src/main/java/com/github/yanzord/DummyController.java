package com.github.yanzord;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    private final DummyProducer producer;

    public DummyController(DummyProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public Dummy sendMessage(Dummy dummy) {
        return producer.sendMessage(dummy);
    }
}
