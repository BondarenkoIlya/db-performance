package com.home.ilya.service

import com.home.ilya.dao.QueryDao
import com.home.ilya.domain.QueryInfo
import spock.lang.Specification

class QueryServiceTest extends Specification {
    def queryDao = Mock(QueryDao)
    def databases = Mock(List)
    def rabbitSender = Mock(RabbitSender)
    def queryService = new QueryService(databases, rabbitSender, queryDao)

    def "findById should fetch query from dao if id not null"() {
        given:
        def uuid = UUID.randomUUID()
        def query = QueryInfo.builder()
                .id(uuid)
                .query("test query")
                .build()

        1 * queryDao.findById(uuid) >> Optional.of(query)

        when:
        def found = queryService.findById(uuid)

        then:
        found.get() == query
    }
}
