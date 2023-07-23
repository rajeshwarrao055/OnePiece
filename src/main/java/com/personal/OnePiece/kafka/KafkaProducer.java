package com.personal.OnePiece.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topicName, String key, String value) {
        var future = kafkaTemplate.send(topicName, key, value);
        future.whenComplete((sendResult, exception) -> {
            if(exception != null) {
                log.error("Failed while dispatching message to topic : {}", topicName, exception);
            } else {
                log.info("Successfully dispatched message to topic : {}", topicName);
            }
        });
    }
}
