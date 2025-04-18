package org.example.functionapi;

import java.util.Map;

public class FunctionDto {
    private String id;
    private String functionName;
    private Map<String, Object> argumentMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Map<String, Object> getArgumentMap() {
        return argumentMap;
    }

    public void setArgumentMap(Map<String, Object> argumentMap) {
        this.argumentMap = argumentMap;
    }
}
