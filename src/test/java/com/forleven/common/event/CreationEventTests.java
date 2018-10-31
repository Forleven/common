package com.forleven.common.event;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@Component
class ModelCreateListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @EventListener
    public void handle(CreationEvent<SampleModel> event) {
        log.info("SampleModel created " + event.getModel());
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreationEventTests {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    public void testSimplePublishEvent() {
        publisher.publishEvent(new CreationEvent<>(new SampleModel()));
    }

}