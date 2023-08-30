package com.personal.OnePiece.flink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.OnePiece.flink.model.Event;
import com.personal.OnePiece.flink.model.OutputStatistics;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class FlinkKafkaStreamingApplication {
    private ObjectMapper objectMapper = new ObjectMapper();

    // Not working , need to debug
    public void streamDataAndLog() {

        log.info("Starting stream on kafka");
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("streaming-topic")
                .setGroupId("streaming-app")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        DataStream<String> dataStream  = streamExecutionEnvironment.fromSource(kafkaSource,
                WatermarkStrategy.noWatermarks(), "Kafka Source");
        DataStream<Event> eventDataStream = dataStream.map(this::parseToEvent);
        DataStream<OutputStatistics> stats = eventDataStream.keyBy(event -> event.getId())
                .window(TumblingEventTimeWindows.of(Time.seconds(10))).aggregate(new SumAggregateFunction());
        stats.print();
        try {
            streamExecutionEnvironment.execute("FlinkKafkaStreamingApplication");
        } catch (Exception ex) {

        }
    }

    @SneakyThrows
    private Event parseToEvent(String data) {
        return objectMapper.readValue(data, Event.class);
    }
}

