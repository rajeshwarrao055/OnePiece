package com.personal.OnePiece.jackson.annotations.serialization.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;

@Builder
public class EntityWithCustomSerializeMethod {
    private String name;
    private String city;
    private String pinCode;

    @JsonValue
    public String getSerialized() {
        return name;
    }
}
