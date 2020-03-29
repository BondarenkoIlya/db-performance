package com.home.ilya.service;

import com.home.ilya.domain.QueryInfo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RabbitSender {

    private final List<Queue> databaseQueues;

    @Autowired
    private RabbitTemplate template;

    public void send(QueryInfo queryInfo) {
        databaseQueues.forEach(queue -> template.convertAndSend(queue.getName(), queryInfo));
    }

}
