package com.personal.OnePiece.serde;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ser-de")
@RestController
public class SerDeController {

    @Autowired
    private SerDeExampleApplication serDeExampleApplication;

    @GetMapping("/serialize")
    public String serialize() {
        return serDeExampleApplication.serializedResult();
    }

    @GetMapping("/deserialize")
    public void deserialize() {
        serDeExampleApplication.deserializeString();
    }
}
