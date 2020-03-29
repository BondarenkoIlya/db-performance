package com.home.ilya.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceResult implements Serializable {
    private static final long serialVersionUID = 483105327576037574L;

    private String databaseType;
    private Long executionTime;
    private PerformanceTestStatus performanceTestStatus;
}
