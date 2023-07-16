package com.personal.OnePiece.redis.data;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@RedisHash(value = "Student", timeToLive = 60L)
public class Student implements Serializable {
    public enum Gender {
        MALE,
        FEMALE
    }

    private String id;
    private String name;
    private Gender gender;
    private int grade;
    @TimeToLive
    private Long expirationInSeconds;
}
