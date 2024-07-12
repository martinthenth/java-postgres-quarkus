package com.tales.terra.web;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class JsonCustomizer implements ObjectMapperCustomizer {
    @Override
    public int priority() {
        return MINIMUM_PRIORITY;
    }

    @Override
    public void customize(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime datetime, JsonGenerator generator, SerializerProvider provider)
                    throws IOException {
                generator.writeString(datetime.toString());
            }
        });

        mapper.registerModule(module);
    }
}