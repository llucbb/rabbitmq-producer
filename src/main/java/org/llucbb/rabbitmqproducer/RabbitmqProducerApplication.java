package org.llucbb.rabbitmqproducer;

import lombok.RequiredArgsConstructor;
import org.llucbb.rabbitmqproducer.model.Employee;
import org.llucbb.rabbitmqproducer.service.EmployeeProducerService;
import org.llucbb.rabbitmqproducer.service.HelloRabbitProducerService;
import org.llucbb.rabbitmqproducer.service.HumanResourceProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
//@EnableScheduling
@RequiredArgsConstructor
public class RabbitmqProducerApplication implements CommandLineRunner {

    private final HelloRabbitProducerService helloRabbitProducerService;
    private final EmployeeProducerService employeeProducerService;
    private final HumanResourceProducerService humanResourceProducerService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //hello message
        helloRabbitProducerService.sendHello("Lucas " + Math.random());

        //employee JSON message
        for (int i = 0; i < 5; i++) {
            var e = Employee.builder()
                    .id("emp" + i)
                    .name("Employee " + i)
                    .birthDate(LocalDate.now())
                    .build();
            employeeProducerService.sendMessage(e);
        }

        //fanout exchange to accounting and marketing queues
        for (int i = 0; i < 5; i++) {
            var e = Employee.builder()
                    .id("emp" + i)
                    .name("Employee " + i)
                    .birthDate(LocalDate.now())
                    .build();
            humanResourceProducerService.sendMessage(e);
        }

        System.exit(1);
    }
}
