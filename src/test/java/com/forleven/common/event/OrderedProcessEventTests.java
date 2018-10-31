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
class ProcessListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @EventListener(condition = "#event.isFirst()")
    public void handle(OrderedProcessEvent<SampleModel> event) {
        log.info("Initial ordered process - step 1");
        event.nextEvent().ifPresent(publisher::publishEvent);
    }

    @EventListener(condition = "#event.step == 2") // or @EventListener(condition = "#event.isSecond()")
    public void handle1(OrderedProcessEvent<SampleModel> event) {
        log.info("ordered process - step 2");
        event.nextEvent().ifPresent(publisher::publishEvent);
    }

    @EventListener(condition = "#event.isThird()")
    public void handle2(OrderedProcessEvent<SampleModel> event) {
        log.info("finish process - step 3");
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderedProcessEventTests {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    public void testSimplePublishEvent() {
        publisher.publishEvent(new OrderedProcessEvent<>(new SampleModel())); // option 1
        publisher.publishEvent(new OrderedProcessEvent<>(1, new SampleModel())); // option 2
        publisher.publishEvent(OrderedProcessEvent.start(new SampleModel())); // option 3
    }
}