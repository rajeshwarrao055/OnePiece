package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.OnePiece.jackson.annotations.serialization.model.EntityWithCustomSerializeMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityWithCustomSerializeMethodTest {

    @Test
    public void testSerializationWithCustomMethod() throws JsonProcessingException {
        EntityWithCustomSerializeMethod entityWithCustomSerializeMethod = EntityWithCustomSerializeMethod
                .builder()
                .name("Rajeshwar")
                .city("Bangalore")
                .pinCode("560100")
                .build();

        String result = new ObjectMapper().writeValueAsString(entityWithCustomSerializeMethod);
        assertEquals(result, "\"Rajeshwar\"");
    }
}