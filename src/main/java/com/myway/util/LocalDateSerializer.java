package com.myway.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2)
            throws IOException {
        arg1.writeString(dateTimeFormatter.format(arg0));
    }
}
