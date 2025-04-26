package org.example.functionapi;

import java.util.Map;
import java.util.Set;

public class FunctionDto {
    private String id;
    private String functionName;
    private Integer functionLevel;
    private Map<String, Object> argumentMap;
    private Set<String> outPutParameterCodeSet;

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

    public Integer getFunctionLevel() {
        return functionLevel;
    }

    public void setFunctionLevel(Integer functionLevel) {
        this.functionLevel = functionLevel;
    }

    public Map<String, Object> getArgumentMap() {
        return argumentMap;
    }

    public void setArgumentMap(Map<String, Object> argumentMap) {
        this.argumentMap = argumentMap;
    }

    public Set<String> getOutPutParameterCodeSet() {
        return outPutParameterCodeSet;
    }

    public void setOutPutParameterCodeSet(Set<String> outPutParameterCodeSet) {
        this.outPutParameterCodeSet = outPutParameterCodeSet;
    }
}
