package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.OnePiece.jackson.annotations.serialization.model.SomeEntity;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SomeEntityTest {

    @Test
    public void testSomeEntitySerialization() throws JsonProcessingException {
        SomeEntity someEntity = SomeEntity.builder()
                .name("rajeshwar")
                .properties(Map.of("city", "Bangalore", "dob", "06-03-1996"))
                .build();

        String result = new ObjectMapper().writeValueAsString(someEntity);
        assertTrue(result.contains("dob"));
    }

}