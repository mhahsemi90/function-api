package org.example.functionapi;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FunctionImpl implements Function {
    static class Person{
        private String name;
        private BigDecimal value;

        public Person() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }
    }
    @Override
    public FunctionResult execute(FunctionDto functionDto, Map<Long, Map<String, Object>> elementInputMap, Map<String, Object> shareInputMap) {
        Map<String, Object> inputMap = elementInputMap.get(1L);
        List<String> value =
                getRootNode(inputMap.get("PERSON"))
                        .goToNode("value")
                        .toObjectList(String.class);
        System.out.println(value.getClass().getCanonicalName());
        value.forEach(System.out::println);
        System.out.println(value);
        return null;
    }
}
