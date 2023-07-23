package com.personal.OnePiece.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ReactiveKafkaProducer reactiveKafkaProducer;

    private static final String TOPIC_NAME = "dispatchTopic";

    @GetMapping("/dispatch")
    public ResponseEntity<Long> dispatchMessages() {
        long startTime = System.currentTimeMillis();
        for(int i=0;i<100000;i++) {
            kafkaProducer.send(TOPIC_NAME, "123", "MESSAGE");
        }
        long endTime = System.currentTimeMillis();
        return ResponseEntity.ok(endTime - startTime);
    }

    @GetMapping("/reactive-dispatch")
    public ResponseEntity<Long> dispatchMessagesReactive() {
        long startTime = System.currentTimeMillis();
        for(int i=0;i<100000;i++) {
            reactiveKafkaProducer.dispatchReactive(TOPIC_NAME, "234", "MESSAGE");
        }
        long endTime = System.currentTimeMillis();
        return ResponseEntity.ok(endTime - startTime);
    }
}
