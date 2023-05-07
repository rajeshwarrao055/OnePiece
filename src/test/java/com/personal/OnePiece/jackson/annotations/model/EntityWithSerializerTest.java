package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.OnePiece.jackson.annotations.serialization.model.EntityWithSerializer;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EntityWithSerializerTest {

    @Test
    public void customSerializationTest() throws JsonProcessingException {
        EntityWithSerializer entityWithSerializer = EntityWithSerializer.builder()
                .name("rajeshwar")
                .eventDate(Date.from(Instant.ofEpochMilli(1683470175000l)))
                .build();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String result = objectMapper.writeValueAsString(entityWithSerializer);
        assertTrue(result.contains("07-05-2023"));
    }

}