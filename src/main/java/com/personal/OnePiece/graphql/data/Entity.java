package com.personal.OnePiece.graphql.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Entity {
    private String id;
    private String name;
    private Integer age;
    private String emailId;
}
