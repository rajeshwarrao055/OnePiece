package com.personal.OnePiece.flink.datastream;

import org.apache.flink.api.java.tuple.Tuple0;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.springframework.stereotype.Component;

@Component
public class DatastreamExample {
    public void javaTuples() {
        Tuple0 tuple0 = new Tuple0();
        Tuple1<String> tuple1 = Tuple1.of("SOMETHING");
        Tuple2<String, Integer> tuple2 = Tuple2.of("SOMETHING", 23);
    }
}
