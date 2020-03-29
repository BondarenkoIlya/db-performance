package com.home.ilya.service;

import com.home.ilya.dao.QueryDao;
import com.home.ilya.domain.PerformanceResult;
import com.home.ilya.domain.PerformanceTestStatus;
import com.home.ilya.domain.QueryInfo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final List<String> databases;
    private final RabbitSender rabbitSender;
    private final QueryDao queryDao;

    public Optional<QueryInfo> findById(UUID id) {
        return queryDao.findById(id);
    }

    public QueryInfo save(QueryInfo queryInfo) {
        databases.forEach(s -> queryInfo.getPerfResults().add(PerformanceResult
                .builder()
                .databaseType(s)
                .performanceTestStatus(PerformanceTestStatus.READY)
                .build())
        );
        queryDao.save(queryInfo);
        rabbitSender.send(queryInfo);
        return queryInfo;
    }

    public List<QueryInfo> findAll() {
        return queryDao.getAll();
    }
}
