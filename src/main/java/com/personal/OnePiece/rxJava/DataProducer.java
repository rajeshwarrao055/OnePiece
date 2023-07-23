package com.personal.OnePiece.rxJava;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class DataProducer {
    public Flux<Integer> produceData() {
        return Flux.range(1, 1000).delayElements(Duration.ofMillis(100));
    }
}
