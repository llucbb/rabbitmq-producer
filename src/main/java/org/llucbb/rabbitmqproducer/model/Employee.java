package org.llucbb.rabbitmqproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Employee {

    private String id;
    private String name;
    private LocalDate birthDate;

}
