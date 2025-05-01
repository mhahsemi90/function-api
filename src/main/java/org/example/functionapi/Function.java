package org.example.functionapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Function {
    class FunctionNode {
        private final JsonNode rootJsonNode;
        private final JsonNodeFactory nodeFactory;
        private final ObjectMapper objectMapper;
        private JsonNode transientJsonNode;

        public FunctionNode(String objectJson, ObjectMapper mapper) throws JsonProcessingException {
            this.objectMapper = mapper;
            this.nodeFactory = mapper.getNodeFactory();
            this.rootJsonNode = mapper.readTree(objectJson);
        }

        public FunctionNode getNode() {
            transientJsonNode = null;
            return this;
        }

        public FunctionNode getNode(String fieldName) {
            if (transientJsonNode != null) {
                transientJsonNode = transientJsonNode.get(fieldName);
            } else {
                transientJsonNode = rootJsonNode.get(fieldName);
            }
            return this;
        }

        public Optional<JsonNode> getValue(String fieldName) {
            if (transientJsonNode != null) {
                return Optional.ofNullable(transientJsonNode.get(fieldName));
            } else {
                return Optional.ofNullable(rootJsonNode.get(fieldName));
            }
        }

        public String getStringValue(String fieldName) {
            return getValue(fieldName)
                    .map(JsonNode::asText)
                    .orElse(null);
        }

        public BigDecimal getDecimalValue(String fieldName) {
            return getValue(fieldName)
                    .map(JsonNode::decimalValue)
                    .orElse(null);
        }

        public <T> T getObject(String fieldName, Class<T> aClass) {
            Optional<JsonNode> jsonNode = getValue(fieldName);
            return jsonNode.map(node ->
                    objectMapper
                            .convertValue(
                                    node
                                    , aClass
                            )
            ).orElse(null);
        }
    }

    FunctionResult execute(FunctionDto functionDto, Map<Long, Map<String, Object>> elementInputMap, Map<String, Object> shareInputMap);

    default List<String> argumentList() {
        return new ArrayList<>();
    }

    default FunctionNode getFunctionNode(String objectJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new FunctionNode(objectJson, mapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
