package com.home.ilya.service;

import com.home.ilya.domain.QueryInfo;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class QueryService {

    public Map<String, List<QueryInfo>> map = new HashMap<>();

    public Mono<QueryInfo> saveQuery(QueryInfo queryInfo) {
        return Mono.just(queryInfo)
                .map(queryInfo1 -> {
                    queryInfo1.setId(UUID.randomUUID());
                    List<QueryInfo> queryInfos = map.computeIfAbsent(queryInfo1.getQuery(), s -> new ArrayList<>());
                    queryInfos.add(queryInfo1);
                    return queryInfo1;
                });

    }

    public Mono<Optional<QueryInfo>> findById(String id) {
        return Mono.just(id)
                .map(queryInfoId -> map.values().stream()
                        .flatMap(Collection::stream)
                        .filter(queryInfo -> queryInfo.getId().equals(UUID.fromString(queryInfoId)))
                        .findFirst());
    }

}
