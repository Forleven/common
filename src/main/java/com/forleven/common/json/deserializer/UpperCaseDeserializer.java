package com.forleven.common.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UpperCaseDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser,
                              DeserializationContext deserializationContext) throws IOException {

        return jsonParser.getText().toUpperCase();
    }
}
