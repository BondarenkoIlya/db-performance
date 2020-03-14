package com.home.ilya.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryInfo {
    private UUID id;
    private String query;
    private int version;
    private List<PerformanceResult> perfResult;
}
