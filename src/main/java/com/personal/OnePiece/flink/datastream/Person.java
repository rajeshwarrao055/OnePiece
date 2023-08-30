package com.personal.OnePiece.flink.datastream;

// valid POJO that flink recognizes
public class Person {
    public String name;
    public Integer age;
    // no args constructor
    public Person() {}
    public Person(String name, Integer age) {
        this.age = age;
        this.name = name;
    }
}
