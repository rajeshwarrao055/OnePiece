package com.personal.OnePiece.jackson.annotations.serialization.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.personal.OnePiece.jackson.annotations.serialization.model.serializers.CustomDateSerializer;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntityWithSerializer {
    private String name;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date eventDate;
}
