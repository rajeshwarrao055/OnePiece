package com.personal.OnePiece.flink.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OutputStatistics {
    private String eventNameConcat;
    private Long value;
}
