package com.home.ilya.service;

import com.home.ilya.domain.QueryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = MssqlReceiver.QUEUE_NAME)
@RequiredArgsConstructor
@Component
public class MssqlReceiver {
    public static final String QUEUE_NAME = "mssql";

    private final PerformanceService performanceService;

    @RabbitHandler
    public void receive(QueryInfo in) throws InterruptedException {
        performanceService.calculatePerformance(in, QUEUE_NAME);
    }
}
