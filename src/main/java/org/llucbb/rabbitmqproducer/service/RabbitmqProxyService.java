package org.llucbb.rabbitmqproducer.service;

import org.llucbb.rabbitmqproducer.utility.RabbitmqQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
public class RabbitmqProxyService {

    private static final String API_QUEUES_URL = "http://localhost:15672/api/queues";
    private static final String AUTHORIZATION = "Authorization";

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public List<RabbitmqQueue> getAllQueues() {
        var webClient = WebClient.create(API_QUEUES_URL);
        return webClient.get().header(AUTHORIZATION, createBasicAuthHeaders()).retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RabbitmqQueue>>() {
                }).block(Duration.ofSeconds(10));
    }

    public String createBasicAuthHeaders() {
        var auth = String.format("%s:%s", userName, password);
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
