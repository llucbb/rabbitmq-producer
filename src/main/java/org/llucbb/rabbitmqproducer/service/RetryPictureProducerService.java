package org.llucbb.rabbitmqproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.llucbb.rabbitmqproducer.model.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetryPictureProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Direct exchange to:
     * <ul>
     * <li>q.quideline.image.work for routingKey=*.*.jpg or *.*.png</li>
     * <li>q.quideline.vector.work for routingKey=*.*.svg</li>
     * </ul>
     * Where routingKey=source.size.type of the picture
     */
    public void sendMessage(Picture picture) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.guideline.work", picture.getType(), json);
    }
}
