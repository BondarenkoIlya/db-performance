package com.home.ilya.controller;

import com.home.ilya.domain.QueryInfo;
import com.home.ilya.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/queries")
public class QueryController {

    private final QueryService queryService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryInfo getById(@PathVariable("id") UUID id) {
        return queryService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryInfo> save(@RequestBody QueryInfo queryInfo) {
        QueryInfo saved = queryService.save(queryInfo);
        return created(URI.create("/queries/" + saved.getId())).build();

    }

}
