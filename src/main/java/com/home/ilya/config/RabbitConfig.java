package com.home.ilya.config;

import com.home.ilya.service.MssqlReceiver;
import com.home.ilya.service.PerformanceService;
import com.home.ilya.service.PostgresReceiver;
import com.home.ilya.service.RabbitSender;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue postgresqlQueue() {
        return new Queue("postgresql");
    }

    @Bean
    public Queue mssqlQueue() {
        return new Queue("mssql");
    }

    @Bean
    public List<Queue> databaseQueues() {
        return List.of(postgresqlQueue(), mssqlQueue());
    }

    @Bean
    public RabbitSender sender() {
        return new RabbitSender(databaseQueues());
    }
}
