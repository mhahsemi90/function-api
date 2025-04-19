package org.example.functionapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Function {
    FunctionResult execute(FunctionDto functionDto, Map<String, Map<String, Object>> elementInputMap, Map<String, Object> shareInputMap);

    default List<String> argumentList() {
        return new ArrayList<>();
    }
}
