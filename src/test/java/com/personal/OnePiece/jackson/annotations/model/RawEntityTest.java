package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.OnePiece.jackson.annotations.serialization.model.RawEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RawEntityTest {

    @Test
    public void testRawEntityValue() throws JsonProcessingException {
        RawEntity rawEntity = RawEntity.builder()
                .name("Rajeshwar")
                .json("{\"key\":123}")
                .rawJson("{\"key\":123}")
                .build();

        String result = new ObjectMapper().writeValueAsString(rawEntity);
        System.out.println(result);
        assertTrue(result.contains("{\"key\":123}"));
    }
}