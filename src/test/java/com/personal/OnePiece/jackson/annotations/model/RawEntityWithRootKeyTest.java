package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.personal.OnePiece.jackson.annotations.serialization.model.RawEntityWithRootKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RawEntityWithRootKeyTest {

    @Test
    public void testSerializationWithRootKey() throws JsonProcessingException {
        RawEntityWithRootKey rawEntityWithRootKey = RawEntityWithRootKey.builder()
                .name("something")
                .json("{\"key\" : 23}")
                .rawJson("{\"key\" : 23}")
                .build();
        final ObjectMapper objectMapper = new ObjectMapper();
        // without enabling this property won't be wrapped in root name
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final String result = objectMapper.writeValueAsString(rawEntityWithRootKey);
        assertTrue(result.contains("RawEntity"));
    }
}