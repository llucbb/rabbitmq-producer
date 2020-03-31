package org.llucbb.rabbitmqproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.llucbb.rabbitmqproducer.model.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringRetryPictureProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Picture picture) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.spring.work", picture.getType(), json);
    }
}
