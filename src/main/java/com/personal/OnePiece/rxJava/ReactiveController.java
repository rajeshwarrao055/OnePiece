package com.personal.OnePiece.rxJava;

import com.personal.OnePiece.rxJava.model.GithubUser;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/reactive")
public class ReactiveController {

    @Autowired
    private GithubService githubService;

    @Autowired
    private DataProducer dataProducer;

    @Autowired
    private DataConsumer dataConsumer;

    @GetMapping("/users/{username}")
    public Mono<GithubUser> reactorExampleApi(@PathVariable String username) {
        return githubService.getUserByUsername(username);
    }

    @SneakyThrows
    @GetMapping("/sync")
    public String syncEndpoint() {
        Thread.sleep(5000);
        return "SYNC API";
    }

    @GetMapping("/async")
    public Mono<String> monoEndpoint() {
        return Mono.just("ASYNC API").delayElement(Duration.ofMillis(5000));
    }

    @GetMapping("/trigger")
    public void beginConsumption() {
        /**
         * Backpressure in action below : Producer emits data every 100 ms, consumer takes 200 ms to process each data item
         * backpressure occurs. Subscriber consumes data in a controlled manner and doesn't get overwhelmed
         */
        Flux<Integer> data = dataProducer.produceData();
        dataConsumer.consumeData(data);
    }
}
