package com.personal.OnePiece.jackson.annotations.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@JsonPropertyOrder({"address", "name"})
@Builder
@Getter
public class AnotherEntity {
    private String name;
    private String address;
}
