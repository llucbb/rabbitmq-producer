package org.llucbb.rabbitmqproducer.service;

import org.llucbb.rabbitmqproducer.utility.RabbitmqQueue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
public class RabbitmqProxyService {

    public List<RabbitmqQueue> getAllQueues() {
        var webClient = WebClient.create("http://localhost:15672/api/queues");
        return webClient.get().header("Authorization", createBasicAuthHeaders()).retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RabbitmqQueue>>() {
                }).block(Duration.ofSeconds(10));
    }

    public String createBasicAuthHeaders() {
        var auth = "guest:vfWxy33zDGmZzxJ";
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
