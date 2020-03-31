package org.llucbb.rabbitmqproducer.utility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitmqQueue {

    @JsonProperty
    private long messages;

    @JsonProperty
    private String name;

    public boolean isDirty() {
        return messages > 0;
    }
}
