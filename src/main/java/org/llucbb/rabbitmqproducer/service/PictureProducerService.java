package org.llucbb.rabbitmqproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.llucbb.rabbitmqproducer.model.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureProducerService {

    private final RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    //fanout exchange to accounting and marketing queues
    public void sendMessage(Picture picture) {
        try {
            var json = objectMapper.writeValueAsString(picture);
            rabbitTemplate.convertAndSend("x.picture", picture.getType(), json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

    }
}
