package com.personal.OnePiece.flink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stream")
@RestController
public class StreamingInvocationController {
    @Autowired
    private FlinkKafkaStreamingApplication flinkKafkaStreamingApplication;
    @Autowired
    private FlinkNaiveApplication flinkNaiveApplication;

    @GetMapping("/init")
    public void initializeStream() {
        //flinkKafkaStreamingApplication.streamDataAndLog();
        flinkNaiveApplication.executeStream();
    }
}
