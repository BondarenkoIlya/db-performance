package com.home.ilya.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceResult {
    private String databaseType;
    private Long executionTime;
    private PerformanceTestStatus performanceTestStatus;
}
