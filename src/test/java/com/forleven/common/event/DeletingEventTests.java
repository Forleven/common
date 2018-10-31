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

import static org.junit.Assert.*;

@Slf4j
@Component
class ModelDeleteListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @EventListener
    public void handle(DeletingEvent<SampleModel> event) {
        log.info("SampleModel deleted " + event.getModel());
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeletingEventTests {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    public void testSimplePublishEvent() {
        publisher.publishEvent(new DeletingEvent<>(new SampleModel()));
    }

}