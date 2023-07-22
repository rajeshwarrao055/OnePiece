package com.personal.OnePiece.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class RegularKafkaProducer {
    public static void main(String[] args) {
        int numMessages = 100;
        String topic = "dispatchTopic";
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "http://172.18.0.3:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        long startTime = System.currentTimeMillis();
        System.out.println("Starting messages dispatch");
        for (int i = 0; i < numMessages; i++) {
            String key = "message-key-" + i;
            String value = "Hello, Kafka! Message " + i;
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            producer.send(record);
        }

        producer.close();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to send messages :: " +  (endTime-startTime) + "ms");
    }
}
