package com.home.ilya.integration

import com.home.ilya.Application
import com.home.ilya.dao.QueryDao
import com.home.ilya.domain.PerformanceResult
import com.home.ilya.domain.PerformanceTestStatus
import com.home.ilya.domain.QueryInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QueryControllerIntegrationTest extends Specification {

    private RestTemplate restTemplate = new RestTemplate()

    @LocalServerPort
    private Long port

    @Autowired
    private QueryDao dao

    def "/queries POST should save new query and return it's location"() {
        given:
        def queryInfo = new QueryInfo(query: "Select * from Test t;")

        when:
        def responseEntity = restTemplate
                .postForEntity(URI.create("http://localhost:${port}/queries"), queryInfo, QueryInfo)


        then:
        responseEntity.statusCode == HttpStatus.CREATED
        responseEntity.getHeaders().get("Location") != null

    }

    def "/queries/{id} GET should get query with performance execution status if exist"() {
        given:
        def generatedQuery = generateQueryInfo()

        when:
        def responseEntity = restTemplate.getForEntity(URI.create("http://localhost:${port}/queries/${generatedQuery.id}"), QueryInfo)


        then:
        responseEntity.statusCode == HttpStatus.OK
        responseEntity.getBody() == generatedQuery

    }

    QueryInfo generateQueryInfo() {
        def performanceResult1 = new PerformanceResult(databaseType: "postgresql", performanceTestStatus: PerformanceTestStatus.FINISH, executionTime: 100)
        def performanceResult2 = new PerformanceResult(databaseType: "mssql", performanceTestStatus: PerformanceTestStatus.IN_PROGRESS)
        def queryInfo = new QueryInfo(query: "Select * from Test t;", id: UUID.randomUUID(), version: 1, perfResults: List.of(performanceResult1, performanceResult2))
        dao.map.put(queryInfo.id, queryInfo)
        return queryInfo
    }
}
