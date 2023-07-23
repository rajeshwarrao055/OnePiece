package com.personal.OnePiece.rxJava;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Log4j2
@Component
public class DataConsumer {

    public Disposable consumeData(final Flux<Integer> dataStream) {
        return dataStream.doOnNext(this::processData).doOnError(this::handleError)
                .doOnComplete(this::handleCompletion).subscribe();
    }

    @SneakyThrows
    private void processData(Integer data) {
        Thread.sleep(200);
        log.info("Received data {}", data);
    }

    private void handleError(Throwable throwable) {
        log.error("Encountered error : ", throwable);
    }

    private void handleCompletion() {
        log.info("Successfull processed data");
    }
}
