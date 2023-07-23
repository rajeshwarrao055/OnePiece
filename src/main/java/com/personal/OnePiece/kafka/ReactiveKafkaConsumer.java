package com.personal.OnePiece.kafka;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log4j2
@Service
public class ReactiveKafkaConsumer {

    @Autowired
    private ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

    @PostConstruct
    public void consume() {
        Disposable disposable = reactiveKafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(this::processMessage)
                .doOnError(throwable -> log.error("Encountered exception", throwable))
                .subscribe();
    }

    @SneakyThrows
    private void processMessage(ConsumerRecord<String, String> consumerRecord) {
        Mono.delay(Duration.ofMillis(100)).then(Mono.fromRunnable(() -> {
            log.info("Received message {}", consumerRecord.value());
        })).subscribe();
    }

}
