package com.home.ilya.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryInfo implements Serializable {
    private static final long serialVersionUID = -7278015943545603733L;

    private UUID id;
    private String query;
    private int version;
    private List<PerformanceResult> perfResults = new ArrayList<>();

    public void updatePerfResultForDatabase(PerformanceResult newPerfResult, String database) {
        perfResults.stream()
                .filter(performanceResult -> performanceResult.getDatabaseType().equals(database))
                .findFirst()
                .ifPresent(performanceResult -> {
                    performanceResult.setPerformanceTestStatus(newPerfResult.getPerformanceTestStatus());
                    performanceResult.setExecutionTime(newPerfResult.getExecutionTime());
                });
    }
}
