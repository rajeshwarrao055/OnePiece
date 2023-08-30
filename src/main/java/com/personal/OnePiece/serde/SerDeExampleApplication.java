package com.personal.OnePiece.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class SerDeExampleApplication {

    @SneakyThrows
    public String serializedResult() {
        User user = new User(1, "rajeshwar");
        Item item = new Item(1, "iPad", user);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Item.class, new ItemSerializer());
        objectMapper.registerModule(simpleModule);

        String serialized = objectMapper.writeValueAsString(item);
        return serialized;
    }
}
