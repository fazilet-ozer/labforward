package com.labforward.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonConverter {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object jsonContent) throws JsonProcessingException {
        return mapper.writeValueAsString(jsonContent);
    }

    public static <T> T parse(String json, Class<T> klass) throws JsonProcessingException {
        return mapper.readerFor(klass).readValue(json);
    }

    public static <T> List<T> parseAsList(String json, Class<T> clazz) throws JsonProcessingException {

        JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, clazz);

        return mapper.readValue(json, type);
    }
}
