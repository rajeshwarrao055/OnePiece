package com.personal.OnePiece.serde;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

// we can register the serializer directly on the class, instead of ObjectMapper

//@JsonSerialize(using = ItemSerializer.class)
@JsonDeserialize(using = ItemDeserializer.class)
@AllArgsConstructor
public class Item {
    public int id;
    public String itemName;
    public User owner;
}
