package com.myway.kafka.producer;

import com.myway.dto.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author Ebru Göksal
 */

public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, EventRequest> kafkaTemplate;

    public void send( EventRequest employee) {
        LOGGER.info("sending employee='{}'", employee.toString());
        kafkaTemplate.send("Kafka_Employee", employee);
    }
}
