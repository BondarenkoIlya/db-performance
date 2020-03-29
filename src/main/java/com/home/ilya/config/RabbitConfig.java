package com.home.ilya.config;

import com.home.ilya.service.RabbitSender;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitConfig {

    @Bean
    public List<Queue> queues(ApplicationContext applicationContext, List<String> databases) {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        return databases.stream()
                .map(database -> {
                    Queue queue = new Queue(database);
                    beanFactory.registerSingleton(database + "Queue", queue);
                    return queue;
                })
                .collect(Collectors.toList());
    }

    @Bean
    public RabbitSender sender(List<Queue> queues) {
        return new RabbitSender(queues);
    }
}
