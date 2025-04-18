package org.example.functionapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Function {
    FunctionResult execute(Map<String, Map<String, Object>> elementInput, FunctionDto functionDto);

    default List<String> argumentList() {
        return new ArrayList<>();
    }
}
