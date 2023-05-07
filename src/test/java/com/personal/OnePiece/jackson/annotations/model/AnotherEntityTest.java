package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.OnePiece.jackson.annotations.serialization.model.AnotherEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnotherEntityTest {

    @Test
    public void testAnotherEntitySerialization() throws JsonProcessingException {
        AnotherEntity anotherEntity = AnotherEntity.builder()
                .address("Address")
                .name("Rajeshwar")
                .build();
        String result = new ObjectMapper().writeValueAsString(anotherEntity);
        assertEquals("{\"address\":\"Address\",\"name\":\"Rajeshwar\"}", result);
    }
}