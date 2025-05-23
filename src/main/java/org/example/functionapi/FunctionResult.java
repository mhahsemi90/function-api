package org.example.functionapi;

import java.util.Map;

public class FunctionResult {
    private Map<Long, Map<String,Object>> functionResultMap;
    private Map<String,Object> someParam;

    public Map<Long, Map<String, Object>> getFunctionResultMap() {
        return functionResultMap;
    }

    public void setFunctionResultMap(Map<Long, Map<String, Object>> functionResultMap) {
        this.functionResultMap = functionResultMap;
    }

    public Map<String, Object> getSomeParam() {
        return someParam;
    }

    public void setSomeParam(Map<String, Object> someParam) {
        this.someParam = someParam;
    }
}
