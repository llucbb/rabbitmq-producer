package org.llucbb.rabbitmqproducer.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Picture {

    private String name;
    private String type;
    private String source;
    private long size;

}
