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

@RunWith(MockitoJUnitRunner.class)
public class JsonMoneyFormatTests {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Sample {

        @JsonProperty("price")
        private BigDecimal price;

        @JsonProperty("price_formatted")
        @JsonMoneyFormat
        public BigDecimal priceFormatted() {
            return price;
        }
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testReturnCurrencyFormatOfAPropertySample() throws IOException {

        String json = mapper.writeValueAsString(new Sample(BigDecimal.TEN));

        String expectedJson = mapper.writeValueAsString(ImmutableMap.of(
                "price", BigDecimal.TEN,
                "price_formatted", "BRL10.00"
        ));

        assertEquals(
                json,
                expectedJson
        );

        String json1 = mapper.writeValueAsString(new Sample(BigDecimal.valueOf(101_121.11111)));

        String expectedJson1 = mapper.writeValueAsString(ImmutableMap.of(
                "price", BigDecimal.valueOf(101_121.11111),
                "price_formatted", "BRL101,121.11"
        ));

        assertEquals(
                json1,
                expectedJson1
        );

        String json2 = mapper.writeValueAsString(new Sample(BigDecimal.ZERO));

        String expectedJson2 = mapper.writeValueAsString(ImmutableMap.of(
                "price", 0,
                "price_formatted", "BRL0.00"
        ));

        assertEquals(
                json2,
                expectedJson2
        );
    }
}
