package com.forleven.common.hateoas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.forleven.common.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static com.forleven.common.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.junit.Assert.*;

@RestController
@RequestMapping("/integration")
class SampleIntegrationController {

    @GetMapping("/get")
    ResponseEntity<String> getSample() {
        return ResponseEntity.ok("Some String");
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerLinkBuilderTests {

    @Test
    public void testLinkToAndMethodOn() {
        assertEquals(
                "http://localhost/integration",
                linkTo(SampleIntegrationController.class).toString()
        );

        assertEquals(
                "http://localhost/integration/get",
                linkTo(methodOn(SampleIntegrationController.class).getSample()).toString()
        );
    }

    @Test
    public void testLinkToAndSlash() {
        assertEquals(
                "http://localhost/integration/1",
                linkTo(SampleIntegrationController.class).slash(1).toString()
        );
    }

}