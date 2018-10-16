package com.forleven.common.web;

import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class ResourcesIntegrationTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSimpleInstanceResources() throws JsonProcessingException {

        PageImpl<String> fakePage = new PageImpl<>(Arrays.asList("school", "education", "learn more"));

        ImmutableMap<String, Object> href = ImmutableMap.of(
                "href", "http://localhost?page=1",
                "number", 1
        );

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

        assertEquals(3, resources.getCount());

        assertEquals(
                mapper.writeValueAsString(expectedJson),
                mapper.writeValueAsString(resources)
        );
    }

    @Test
    public void testSimpleInstanceResourcesWithNextLink() throws JsonProcessingException {

        PageImpl<String> fakePage = new PageImpl<>(
                Arrays.asList("school", "education", "learn more"),
                PageRequest.of(0, 3),
                6
        );

        ImmutableMap<String, Object> href = ImmutableMap.of(
                "href", "http://localhost?page=1",
                "number", 1
        );

        ImmutableMap<String, Object> hrefNext = ImmutableMap.of(
                "href", "http://localhost?page=2",
                "number", 2
        );

        ImmutableMap<String, Object> links = ImmutableMap.of(
                "last", hrefNext,
                "self", href,
                "first", href,
                "next", hrefNext
        );

        ImmutableMap<String, Object> expectedJson = ImmutableMap.of(
                "count", 3,
                "data", Arrays.asList("school", "education", "learn more"),
                "links", links
        );

        Resources<String> resources = new Resources<>(fakePage);

        assertEquals(3, resources.getCount());

        assertEquals(
                mapper.writeValueAsString(expectedJson),
                mapper.writeValueAsString(resources)
        );
    }

    @Test
    public void testSimpleInstanceResourcesWithPreviousLink() throws JsonProcessingException {

        PageImpl<String> fakePage = new PageImpl<>(
                Arrays.asList("school", "education", "learn more"),
                PageRequest.of(1, 3),
                6
        );

        ImmutableMap<String, Object> href = ImmutableMap.of(
                "href", "http://localhost?page=2",
                "number", 2
        );

        ImmutableMap<String, Object> hrefPrevious = ImmutableMap.of(
                "href", "http://localhost?page=1",
                "number", 1
        );

        ImmutableMap<String, Object> links = ImmutableMap.of(
                "last", href,
                "self", href,
                "first", hrefPrevious,
                "previous", hrefPrevious
        );

        ImmutableMap<String, Object> expectedJson = ImmutableMap.of(
                "count", 3,
                "data", Arrays.asList("school", "education", "learn more"),
                "links", links
        );

        Resources<String> resources = new Resources<>(fakePage);

        assertEquals(3, resources.getCount());

        assertEquals(
                mapper.writeValueAsString(expectedJson),
                mapper.writeValueAsString(resources)
        );
    }

    @Test
    public void testSimpleInstanceResourcesWithNextAndPreviousLink() throws JsonProcessingException {

        PageImpl<String> fakePage = new PageImpl<>(
                Arrays.asList("school", "education", "learn more"),
                PageRequest.of(1, 3),
                9
        );

        ImmutableMap<String, Object> hrefLast = ImmutableMap.of(
                "href", "http://localhost?page=3",
                "number", 3
        );

        ImmutableMap<String, Object> hrefSelf = ImmutableMap.of(
                "href", "http://localhost?page=2",
                "number", 2
        );

        ImmutableMap<String, Object> hrefFirst = ImmutableMap.of(
                "href", "http://localhost?page=1",
                "number", 1
        );

        ImmutableMap<String, Object> hrefPrevious = ImmutableMap.of(
                "href", "http://localhost?page=1",
                "number", 1
        );

        ImmutableMap<String, Object> hrefNext = ImmutableMap.of(
                "href", "http://localhost?page=3",
                "number", 3
        );

        ImmutableMap<String, Object> links = ImmutableMap.of(
                "last", hrefLast,
                "self", hrefSelf,
                "first", hrefFirst,
                "previous", hrefPrevious,
                "next", hrefNext
        );

        ImmutableMap<String, Object> expectedJson = ImmutableMap.of(
                "count", 3,
                "data", Arrays.asList("school", "education", "learn more"),
                "links", links
        );

        Resources<String> resources = new Resources<>(fakePage);

        assertEquals(3, resources.getCount());

        assertEquals(
                mapper.writeValueAsString(expectedJson),
                mapper.writeValueAsString(resources)
        );
    }

    @Test
    public void testSimpleInstanceResourcesWithStream() {
        Resources<String> resources = new Resources<>(new PageImpl<>(Collections.singletonList("management")));

        // in real world here is a code with business logic
        String firstResource = resources.stream()
                .map(String::toUpperCase)
                .findFirst()
                .orElse("");

        assertEquals(
                "MANAGEMENT",
                firstResource
        );
    }

    /**
     * If you return a Optional<Resources<String>>, this static method help you if you want you orElse methods.
     * <pre>
     * {@code
     *  Optional<Resources<String>> businessNullable = someProcess();
     *  businessNullable.orElse(Resources.empty())
     * }
     * </pre>
     */
    @Test
    public void testSimpleInstanceResourcesEmpty() {
        Resources<String> emptyResources = Resources.empty();

        assertEquals(
                0,
                emptyResources.getSize()
        );
    }

}