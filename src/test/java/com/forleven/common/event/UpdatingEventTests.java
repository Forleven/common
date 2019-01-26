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
class ModelUpdateListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @EventListener
    public void handle(UpdatingEvent<SampleModel> event) {
        log.info("SampleModel updated " + event.getModel());
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdatingEventTests {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    public void testSimplePublishEvent() {
        publisher.publishEvent(new UpdatingEvent<>(new SampleModel()));
    }
}