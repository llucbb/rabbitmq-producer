package org.llucbb.rabbitmqproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.llucbb.rabbitmqproducer.json.CustomLocalDateSerializer;

import java.time.LocalDate;

@Data
@Builder
public class Employee {

    @JsonProperty("employee_id")
    private String id;

    private String name;

    @JsonProperty("birth_date")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate birthDate;

}
