package com.forleven.common.json.annotation;

import java.io.IOException;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class JsonMoneyFormatTests {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Sample {

        @JsonProperty("price_formatted")
        @JsonMoneyFormat
        private BigDecimal priceFormatted;
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testReturnCurrencyFormatOfAPropertySample() throws IOException {

        String json = mapper.writeValueAsString(new Sample(BigDecimal.TEN));

        assertEquals(
                json,
                mapper.writeValueAsString(ImmutableMap.of("price_formatted", "BRL10.00"))
        );

        String json1 = mapper.writeValueAsString(new Sample(BigDecimal.valueOf(101_121.11111)));

        assertEquals(
                json1,
                mapper.writeValueAsString(ImmutableMap.of("price_formatted", "BRL101,121.11"))
        );

        String json2 = mapper.writeValueAsString(new Sample(BigDecimal.TEN));

        assertEquals(
                json2,
                mapper.writeValueAsString(ImmutableMap.of("price_formatted", "BRL10.00"))
        );
    }
}
