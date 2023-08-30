package com.personal.OnePiece.serde;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ItemDeserializer extends StdDeserializer<Item> {
    public ItemDeserializer(Class<?> vc) {
        super(vc);
    }

    public ItemDeserializer() {
        this(null);
    }

    @Override
    public Item deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        int itemId = jsonNode.get("id").asInt();
        String itemName = jsonNode.get("itemName").asText();
        JsonNode user = jsonNode.get("owner");
        int userId = user.get("id").asInt();
        String userName = user.get("name").asText();

        return new Item(itemId, itemName, new User(userId, userName));
    }
}
