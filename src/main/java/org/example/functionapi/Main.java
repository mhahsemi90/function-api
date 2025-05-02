package org.example.functionapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static class Person{
        private String name;
        private List<String> value;

        public Person(String name, List<String> value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
    public static void main(String[] args) throws JsonProcessingException {
        Map<Long, Map<String, Object>> elementInputMap = new LinkedHashMap<>();
        Map<String, Object> personMap = new LinkedHashMap<>();
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Java");
        Person person = new Person("test",stringList);
        ObjectMapper objectMapper = new ObjectMapper();
        personMap.put("PERSON", objectMapper.writeValueAsString(person));
        elementInputMap.put(1L, personMap);
        FunctionImpl functionImpl = new FunctionImpl();
        functionImpl.execute(null,elementInputMap,null);
    }
}
