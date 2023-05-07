package com.personal.OnePiece.jackson.annotations.serialization.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@JsonRootName("RawEntity")
@Getter
@Builder
public class RawEntityWithRootKey {
    private String name;
    private String rawJson;
    private String json;
}
