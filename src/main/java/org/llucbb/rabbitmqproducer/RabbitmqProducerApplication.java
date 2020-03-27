package org.llucbb.rabbitmqproducer;

import lombok.RequiredArgsConstructor;
import org.llucbb.rabbitmqproducer.service.HelloRabbitProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class RabbitmqProducerApplication implements CommandLineRunner {

    private final HelloRabbitProducerService helloRabbitProducerService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        helloRabbitProducerService.sendHello("Lucas " + Math.random());
    }
}
