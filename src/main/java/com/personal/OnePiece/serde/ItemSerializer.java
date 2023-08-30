package com.personal.OnePiece.serde;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ItemSerializer extends StdSerializer<Item> {

    public ItemSerializer(Class<Item> t) {
        super(t);
    }

    public ItemSerializer() {
        this(null);
    }

    @Override
    public void serialize(Item value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.id);
        gen.writeStringField("itemName", value.itemName);
        gen.writeObjectFieldStart("owner");
        gen.writeNumberField("id", value.owner.id);
        gen.writeStringField("name", value.owner.name);
        gen.writeEndObject();
        gen.writeEndObject();
    }
}
