package org.llucbb.rabbitmqproducer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RabbitmqRestController {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RabbitTemplate rabbitTemplate;

    public static boolean isValidJson(String json) {
        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @PostMapping(path = {"/api/publish/{exchange}/{routingKey}", "/api/publish/{exchange}"}, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> publish(@PathVariable(name = "exchange") String exchange,
                                          @PathVariable(name = "routingKey", required = false) String routingKey,
                                          @RequestBody String message) {
        if (!isValidJson(message)) {
            return ResponseEntity.badRequest().body(Boolean.FALSE.toString());
        }

        try {
            rabbitTemplate.convertAndSend(exchange, StringUtils.isEmpty(routingKey) ? "" : routingKey, message);
            return ResponseEntity.ok(Boolean.TRUE.toString());
        } catch (Exception e) {
            log.error("Error when publishing : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
