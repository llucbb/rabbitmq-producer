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
public class MyPictureProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Fanout exchange to q.mypicture.image with a dead letter exchange (x-dead-letter-exchange) to q.mypicture.dlx, in
     * order to handle errors at consumer. When the consumer throws AmqpRejectAndDontRequeueException the message will
     * be removed from q.mypicture-image and queued into q.mypicture.dlx for further error processing.
     */
    public void sendMessage(Picture picture) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.mypicture", "", json);
    }
}
