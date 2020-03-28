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
public class PictureTwoProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Topic exchange to:
     * <ul>
     * <li>q.picture.image for routingKey=*.*.jpg or *.*.png</li>
     * <li>q.picture.vector for routingKey=*.*.svg</li>
     * <li>q.picture.filter for routingKey=mobile.#</li>
     * <li>q.picture.log for routingKey=*.large.svg</li>
     * </ul>
     * Where routingKey=source.size.type of the picture
     */
    public void sendMessage(Picture picture) throws JsonProcessingException {
        var routingKey = new StringBuilder();
        routingKey.append(picture.getSource());
        routingKey.append(".");
        if (picture.getSize() > 4000) {
            routingKey.append("large");
        } else {
            routingKey.append("small");
        }
        routingKey.append(".");
        routingKey.append(picture.getType());

        var json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.picture2", routingKey.toString(), json);
    }
}
