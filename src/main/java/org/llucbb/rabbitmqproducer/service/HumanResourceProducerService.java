package org.llucbb.rabbitmqproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.llucbb.rabbitmqproducer.model.Employee;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HumanResourceProducerService {

    private final RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    //fanout exchange to accounting and marketing queues
    public void sendMessage(Employee employee) {
        try {
            var json = objectMapper.writeValueAsString(employee);
            rabbitTemplate.convertAndSend("x.hr", "", json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

    }
}
