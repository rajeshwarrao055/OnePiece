package com.personal.OnePiece.jackson.annotations.serialization.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Builder;

import java.util.Map;

@Builder
public class SomeEntity {
    public String name;
    private Map<String, String> properties;

    /*
    JsonAnyGetter -> all key values in the map are serialized
    at the parent level instead of nested json
     */
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
}
