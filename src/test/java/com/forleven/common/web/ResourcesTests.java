package com.forleven.common.web;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class ResourcesTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSimpleInstanceResources() throws JsonProcessingException {

        PageImpl<String> fakePage = new PageImpl<>(Arrays.asList("school", "education", "learn more"));

        ImmutableMap<String, String> href = ImmutableMap.of("href", "http://localhost?page=1");

        ImmutableMap<String, Object> links = ImmutableMap.of(
                "last", href,
                "self", href,
                "first", href
        );

        ImmutableMap<String, Object> expectedJson = ImmutableMap.of(
                "count", 3,
                "data", Arrays.asList("school", "education", "learn more"),
                "links", links
        );

        Resources<String> resources = new Resources<>(fakePage);

        assertEquals(resources.getCount(), 3);

        assertEquals(
                mapper.writeValueAsString(expectedJson),
                mapper.writeValueAsString(resources)
        );
    }

}