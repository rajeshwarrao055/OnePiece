package com.personal.OnePiece.kafka;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ReactiveKafkaProducer {

    @Autowired
    public ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

    public void dispatchReactive(String topicName, String key, String value) {
        var response = reactiveKafkaProducerTemplate.send(topicName, key, value);
        response.doOnNext(record -> {
            log.info("Successfully dispatched message", record.recordMetadata().offset());
        }).doOnError(throwable -> {
            log.error("Failed dispatching message to topic", throwable);
        }).subscribe();

    }

}
