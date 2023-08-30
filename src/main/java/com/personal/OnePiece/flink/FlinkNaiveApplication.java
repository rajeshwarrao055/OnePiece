package com.personal.OnePiece.flink;

import com.personal.OnePiece.flink.model.Person;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlinkNaiveApplication {

    public void executeStream() {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment
                .getExecutionEnvironment();
        DataStream<Person> dataStream = environment.fromElements(
                new Person("Rajeshwar", 27),
                new Person("Somebody", 23),
                new Person("Nobody", 28),
                new Person("Everybody", 29)
        );

        DataStream<Person> adults = dataStream.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.getAge() >= 23;
            }
        });
        // lambda for above
        //DataStream<Person> adults = dataStream.filter((FilterFunction<Person>) person -> person.getAge() >= 23);
        try {
            final List<Person> filteredList = adults.executeAndCollect(1000);
            System.out.println(filteredList.size());
            environment.execute();
            // getting a no operators defined in streaming topology, valid exception -> need to add operators in data stream directed graph
        } catch (Exception ex) {
            System.out.println("Error while executing streaming environment");
        }
    }
}
