package com.personal.OnePiece.flink;

import com.personal.OnePiece.flink.model.Event;
import com.personal.OnePiece.flink.model.OutputStatistics;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class SumAggregateFunction implements AggregateFunction<Event, Tuple2<String, Integer>, OutputStatistics> {
    @Override
    public Tuple2<String, Integer> createAccumulator() {
        return new Tuple2<>("", 0);
    }

    @Override
    public Tuple2<String, Integer> add(Event value, Tuple2<String, Integer> accumulator) {
        return new Tuple2<>(value.getEventName(), accumulator.f1 + value.getValue());
    }

    @Override
    public OutputStatistics getResult(Tuple2<String, Integer> accumulator) {
        return new OutputStatistics(accumulator.f0, accumulator.f1.longValue());
    }

    @Override
    public Tuple2<String, Integer> merge(Tuple2<String, Integer> a, Tuple2<String, Integer> b) {
        return new Tuple2<>(a.f0 + "_" + b.f0, a.f1 + b.f1);
    }
}
