package com.home.ilya.controller

import com.home.ilya.Application
import com.home.ilya.domain.PerformanceResult
import com.home.ilya.domain.PerformanceTestStatus
import com.home.ilya.domain.QueryInfo
import com.home.ilya.service.QueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QueryControllerIntegrationTest extends Specification {

    @Autowired
    private WebClient webClient

    @LocalServerPort
    private Long port

    @Autowired
    private QueryService queryService

    def "/queries POST should save new query and return it's location"() {
        given:
        def queryInfo = new QueryInfo(query: "Select * from Test t;")

        when:
        def mono = webClient
                .post()
                .uri(URI.create("http://localhost:${port}/queries"))
                .body(BodyInserters.fromValue(queryInfo))
                .exchange()

        then:
        StepVerifier.create(mono)
                .expectNextMatches({ response ->
                    return response.statusCode().value() == 201 && response.headers().header("Location") != null
                })
                .verifyComplete()
    }

    def "/queries/{id} GET should get query with performance execution status if exist"() {
        given:
        def generatedQuery = generateQueryInfo()

        when:
        def mono = ((WebClient.RequestHeadersUriSpec) webClient.get()
                .uri(URI.create("http://localhost:${port}/queries/${generatedQuery.id}")))
                .exchange()

        then:
        Mono<QueryInfo> buffer

        StepVerifier.create(mono)
                .consumeNextWith({ response -> buffer = response.bodyToMono(QueryInfo) })
                .verifyComplete()

        StepVerifier.create(buffer)
                .expectNext(generatedQuery)
                .verifyComplete()
    }

    QueryInfo generateQueryInfo() {
        def performanceResult1 = new PerformanceResult(databaseType: "PostgreSQL", performanceTestStatus: PerformanceTestStatus.FINISH, executionTime: 100)
        def performanceResult2 = new PerformanceResult(databaseType: "Oracle", performanceTestStatus: PerformanceTestStatus.IN_PROGRESS)
        def queryInfo = new QueryInfo(query: "Select * from Test t;", id: UUID.randomUUID(), version: 1, perfResult: List.of(performanceResult1, performanceResult2))
        def values = queryService.map.computeIfAbsent("Select * from Test t;", { s -> new ArrayList<>() })
        values.add(queryInfo)
        return queryInfo
    }
}
