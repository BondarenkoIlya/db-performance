package com.home.ilya.service;

import com.home.ilya.dao.QueryDao;
import com.home.ilya.domain.PerformanceResult;
import com.home.ilya.domain.PerformanceTestStatus;
import com.home.ilya.domain.QueryInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final QueryDao queryDao;

    public void calculatePerformance(QueryInfo queryInfo, String database) throws InterruptedException {
        Optional<QueryInfo> optionalQueryInfo = queryDao.findById(queryInfo.getId());
        optionalQueryInfo.ifPresent(queryInfo1 -> queryInfo1.updatePerfResultForDatabase(PerformanceResult.builder().performanceTestStatus(PerformanceTestStatus.IN_PROGRESS).build(), database));
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("PerformanceService for " + database + ": Some work perform after message {" + queryInfo + "} received");
        Thread.sleep(3000);
        System.out.println("PerformanceService for " + database + ": Work with message {" + queryInfo + "} is done");
        watch.stop();
        System.out.println("PostgresReceiver: Spent " + watch.getTotalTimeMillis());
        optionalQueryInfo.ifPresent(queryInfo1 -> queryInfo1.updatePerfResultForDatabase(PerformanceResult.builder().performanceTestStatus(PerformanceTestStatus.FINISH).executionTime(watch.getTotalTimeMillis()).build(), database));

    }
}
