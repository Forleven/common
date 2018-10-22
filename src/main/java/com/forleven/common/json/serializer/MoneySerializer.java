package com.forleven.common.json.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MoneySerializer extends JsonSerializer<BigDecimal> {

    public void serialize(BigDecimal value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider provider) throws IOException {

        Currency currency = Currency.getInstance("BRL");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(currency);

        jsonGenerator.writeString(numberFormat.format(value));
    }
}
