package com.home.ilya.service;

import com.home.ilya.dao.QueryDao;
import com.home.ilya.dao.QueryExecutor;
import com.home.ilya.domain.PerformanceResult;
import com.home.ilya.domain.PerformanceTestStatus;
import com.home.ilya.domain.QueryInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final QueryDao queryDao;
    private final QueryExecutor queryExecutor;

    public void calculatePerformance(QueryInfo queryInfo, String database) {
        QueryInfo foundQueryInfo = queryDao.findById(queryInfo.getId())
                .orElseThrow(NoSuchElementException::new);
        try {
            foundQueryInfo.updatePerfResultForDatabase(PerformanceResult.builder().performanceTestStatus(PerformanceTestStatus.IN_PROGRESS).build(), database);
            StopWatch watch = new StopWatch();
            watch.start();
            System.out.println("PerformanceService for " + database + ": Some work perform after message {" + queryInfo + "} received");
            queryExecutor.execute(foundQueryInfo.getQuery(), database);
            System.out.println("PerformanceService for " + database + ": Work with message {" + queryInfo + "} is done");
            watch.stop();
            System.out.println("PostgresReceiver: Spent " + watch.getTotalTimeMillis());
            foundQueryInfo.updatePerfResultForDatabase(PerformanceResult.builder().performanceTestStatus(PerformanceTestStatus.FINISH).executionTime(watch.getTotalTimeMillis()).build(), database);
        } catch (Exception e) {
            foundQueryInfo.updatePerfResultForDatabase(PerformanceResult.builder().performanceTestStatus(PerformanceTestStatus.ERROR).build(), database);
        }
    }
}
