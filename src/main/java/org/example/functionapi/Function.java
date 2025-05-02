package org.example.functionapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Function {
    FunctionResult execute(FunctionDto functionDto, Map<Long, Map<String, Object>> elementInputMap, Map<String, Object> shareInputMap);

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
}
