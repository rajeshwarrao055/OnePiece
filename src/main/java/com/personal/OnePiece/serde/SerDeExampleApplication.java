package com.personal.OnePiece.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class SerDeExampleApplication {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Item.class, new ItemSerializer());
        objectMapper.registerModule(simpleModule);
    }

    @SneakyThrows
    public String serializedResult() {
        User user = new User(1, "rajeshwar");
        Item item = new Item(1, "iPad", user);
        String serialized = objectMapper.writeValueAsString(item);
        return serialized;
    }

    @SneakyThrows
    public void deserializeString() {
        String serializedString = serializedResult();
        Item item = objectMapper.readValue(serializedString, Item.class);
        System.out.println(item.id);
    }
}
