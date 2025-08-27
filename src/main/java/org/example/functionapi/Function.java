package org.example.functionapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Function {
    FunctionResult execute(FunctionDto functionDto, Map<Long, Map<String, Object>> elementInputMap, Map<String, Object> shareInputMap);

    Set<Class<?>> getClassSet();

    default void initiate(){
        for (Class<?> aClass : getClassSet()) {
            try {
                aClass.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    default List<String> argumentList() {
        return new ArrayList<>();
    }

    default RootNode getRootNode(Object objectJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new RootNode(objectJson.toString(), mapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    default String serializeValue(Object value){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
