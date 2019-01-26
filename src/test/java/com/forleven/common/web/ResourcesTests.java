package com.forleven.common.web;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ResourcesTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSimpleInstance() throws JsonProcessingException {
        PageImpl<String> fakePage = new PageImpl<>(Arrays.asList("school", "education", "learn more"));

        ImmutableMap<String, Object> expectedJson = ImmutableMap.of(
                "count", 3,
                "data", Arrays.asList("school", "education", "learn more"),
                "links", ImmutableMap.of()
        );

        Resources<String> resources = new Resources<>(fakePage);

        assertEquals(3, resources.getCount());

        assertEquals(
                mapper.writeValueAsString(expectedJson),
                mapper.writeValueAsString(resources)
        );
    }

}
