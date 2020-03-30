package org.llucbb.rabbitmqproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.llucbb.rabbitmqproducer.model.Employee;
import org.llucbb.rabbitmqproducer.model.Picture;
import org.llucbb.rabbitmqproducer.service.EmployeeProducerService;
import org.llucbb.rabbitmqproducer.service.HelloRabbitProducerService;
import org.llucbb.rabbitmqproducer.service.HumanResourceProducerService;
import org.llucbb.rabbitmqproducer.service.MyPictureProducerService;
import org.llucbb.rabbitmqproducer.service.PictureProducerService;
import org.llucbb.rabbitmqproducer.service.PictureTwoProducerService;
import org.llucbb.rabbitmqproducer.service.RetryEmployeeProducerService;
import org.llucbb.rabbitmqproducer.service.RetryPictureProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
//@EnableScheduling
@RequiredArgsConstructor
public class RabbitmqProducerApplication implements CommandLineRunner {

    private final HelloRabbitProducerService helloRabbitProducerService;
    private final EmployeeProducerService employeeProducerService;
    private final HumanResourceProducerService humanResourceProducerService;
    private final PictureProducerService pictureProducerService;
    private final PictureTwoProducerService pictureTwoProducerService;
    private final MyPictureProducerService myPictureProducerService;
    private final RetryPictureProducerService retryPictureProducerService;
    private final RetryEmployeeProducerService retryEmployeeProducerService;

    private final static List<String> SOURCES = List.of("mobile", "web");
    private final static List<String> TYPES = List.of("jpg", "png", "svg");

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws JsonProcessingException {
        // Hello message
        //helloRabbitProducerService.sendHello("Lucas " + Math.random());

        // Employee JSON message
        for (int i = 0; i < 5; i++) {
            var employee = Employee.builder()
                    .id("emp" + i)
                    .name("Employee " + i)
                    .birthDate(LocalDate.now())
                    .build();
            //employeeProducerService.sendMessage(employee);
        }

        // Fanout exchange to accounting and marketing queues
        for (int i = 0; i < 5; i++) {
            var employee = Employee.builder()
                    .id("emp" + i)
                    .name("Employee " + i)
                    .birthDate(LocalDate.now())
                    .build();
            //humanResourceProducerService.sendMessage(employee);
        }

        // Direct exchange to picture image and vector queues
        for (int i = 0; i < 10; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(1, 1001))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            //pictureProducerService.sendMessage(picture);
        }

        // Topic exchange
        for (int i = 0; i < 10; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(1, 10000))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            //pictureTwoProducerService.sendMessage(picture);
        }

        // Dead letter exchange
        for (int i = 0; i < 1; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(9001, 10001))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            //myPictureProducerService.sendMessage(picture);
        }

        // Dead letter exchange using Message/Channel at consumer
        for (int i = 0; i < 10; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(1, 10001))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            //myPictureProducerService.sendMessage(picture);
        }

        // Retry Mechanism for Direct Exchange
        for (int i = 0; i < 10; i++) {
            var picture = Picture.builder()
                    .name("Picture " + i)
                    .size(ThreadLocalRandom.current().nextLong(9001, 10001))
                    .source(SOURCES.get(i % SOURCES.size()))
                    .type(TYPES.get(i % TYPES.size()))
                    .build();
            //retryPictureProducerService.sendMessage(picture);
        }

        // Retry Mechanism for Fanout Exchange
        for (int i = 0; i < 10; i++) {
            Employee emp = Employee.builder()
                    .id("emp" + i)
                    .name(null)
                    .birthDate(LocalDate.now())
                    .build();
            retryEmployeeProducerService.sendMessage(emp);
        }

        System.exit(1);
    }
}
