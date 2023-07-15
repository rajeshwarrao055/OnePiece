package com.personal.OnePiece.graphql;

import com.personal.OnePiece.graphql.data.Entity;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GraphQlDemoController {

    @QueryMapping
    public List<Entity> getData(@Argument int count, @Argument int offset) {
        return demoEntities().stream().limit(count).collect(Collectors.toList());
    }

    private List<Entity> demoEntities() {
        List<Entity> entities = new ArrayList<>();
        entities.add(Entity.builder()
                        .age(23)
                        .id("1")
                        .name("Rajeshwar")
                        .emailId("rajeshwarrao055@gmail.com")
                .build());
        return entities;
    }
}
