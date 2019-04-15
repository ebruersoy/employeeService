package com.myway.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myway.dto.EventRequest;
import com.myway.entity.Employee;
import com.myway.kafka.producer.Sender;
import com.myway.util.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author Ebru Göksal
 */
@Component
public class EventService {
    @Autowired
    private Sender template;

    public void send(EventType topicAdd, Employee convertedEmployee) throws JsonProcessingException {
        EventRequest request = new EventRequest();
        request.setTopic(topicAdd.getCode());
        request.setEventDate(LocalDate.now());
        request.setEmployeeUuid(convertedEmployee.getUuid());
        String employeeJson = new ObjectMapper().writeValueAsString(convertedEmployee);
        request.setEmployeeJson(employeeJson);
        template.send(request);
    }

    public void setTemplate(Sender template) {
        this.template = template;
    }
}
