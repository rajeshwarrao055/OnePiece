package com.personal.OnePiece.graphdb.data;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("Person")
public class PersonEntity {
    @Id
    private final String name;
    private final Integer born;
}
