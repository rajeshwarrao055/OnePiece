package com.personal.OnePiece.jackson.annotations.serialization.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RawEntity {
    private String name;
    /*
    JsonRawValue instructs jackson to serialize a property exactly as is
     */
    @JsonRawValue
    private String rawJson;
    private String json;
}
