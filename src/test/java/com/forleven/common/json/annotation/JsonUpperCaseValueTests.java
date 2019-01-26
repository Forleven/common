package com.forleven.common.json.annotation;

import java.io.IOException;

import lombok.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class JsonUpperCaseValueTests {

    @Data
    public static class Sample {

        @JsonUpperCaseValue
        private String name;
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUpperCaseAPropertySample() throws IOException {

        String inputJson = mapper.writeValueAsString(ImmutableMap.of("name", "education"));

        Sample sample = mapper.readValue(inputJson, Sample.class);

        assertEquals(
                "EDUCATION",
                sample.getName()
        );

        String inputJson1 = mapper.writeValueAsString(ImmutableMap.of("name", "EDUCATION"));

        Sample sample1 = mapper.readValue(inputJson1, Sample.class);

        assertEquals(
                "EDUCATION",
                sample1.getName()
        );

        String inputJson2 = mapper.writeValueAsString(ImmutableMap.of());

        Sample sample2 = mapper.readValue(inputJson2, Sample.class);

        assertNull(sample2.getName());

    }
}
