package com.home.ilya.controller;

import com.home.ilya.domain.QueryInfo;
import com.home.ilya.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.nest(path("/queries"), route()
                .GET("/{id}", getById())
                .POST("", saveQuery())
                .build()
        );
    }

    private HandlerFunction<ServerResponse> saveQuery() {
        return request -> request.bodyToMono(QueryInfo.class)
                .flatMap(queryService::saveQuery)
                .flatMap(queryInfo -> created(URI.create("/queries/" + queryInfo.getId())).build());
    }

    private HandlerFunction<ServerResponse> getById() {
        return request -> Mono.just(request.pathVariable("id"))
                .flatMap(queryService::findById)
                .flatMap(queryInfo -> {
                    if (queryInfo.isPresent()) {
                        return ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(queryInfo.get()));
                    } else {
                        return notFound().build();
                    }
                });
    }
}
