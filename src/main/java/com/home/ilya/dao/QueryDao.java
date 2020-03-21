package com.home.ilya.dao;

import com.home.ilya.domain.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class QueryDao {

    public Map<UUID, QueryInfo> map = new HashMap<>();

    public QueryInfo save(QueryInfo queryInfo) {
        queryInfo.setId(UUID.randomUUID());
        map.put(queryInfo.getId(), queryInfo);
        return queryInfo;
    }

    public Optional<QueryInfo> findById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }
}
