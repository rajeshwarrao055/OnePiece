package com.personal.OnePiece.flink.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class Event {
    private String id;
    private String eventName;
    private Long eventTimestamp;
    private Integer value;
}
