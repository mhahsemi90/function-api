package org.example.functionapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RootNode {
    private final JsonNode rootJsonNode;
    private final ObjectMapper objectMapper;
    private JsonNode transientJsonNode;

    public RootNode(String objectJson, ObjectMapper mapper) throws JsonProcessingException {
        this.objectMapper = mapper;
        this.rootJsonNode = mapper.readTree(objectJson);
    }

    public RootNode(JsonNode rootJsonNode, ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.rootJsonNode = rootJsonNode;
    }

    public RootNode getNode() {
        transientJsonNode = null;
        return this;
    }

    public RootNode goToNode(String fieldName) {
        if (transientJsonNode != null) {
            transientJsonNode = Optional.ofNullable(
                    transientJsonNode.get(fieldName)
            ).orElseThrow(() -> new RuntimeException(
                    "Cannot invoke \"getNode\" because \"" + fieldName + "\" not found"
            ));
        } else {
            if (rootJsonNode == null)
                throw new IllegalArgumentException("rootJsonNode is null");
            transientJsonNode = Optional.ofNullable(
                    rootJsonNode.get(fieldName)
            ).orElseThrow(() -> new RuntimeException(
                    "Cannot invoke \"getNode\" because \"" + fieldName + "\" not found"
            ));
        }
        return this;
    }


    public List<RootNode> toRootNodeList() {
        List<RootNode> result;
        JsonNode jsonNode = transientJsonNode != null ? transientJsonNode : rootJsonNode;
        if (jsonNode == null) {
            throw new IllegalArgumentException("rootJsonNode is null");
        } else if (getType(jsonNode).contains(ExportType.ARRAY)) {
            result = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                result.add(new RootNode(node, objectMapper));
            }
        } else {
            throw new RuntimeException(
                    "Cannot invoke \"toRootNodeList\" because \"" + jsonNode + "\" not Array"
            );
        }
        return result;
    }

    public List<RootNode> toRootNodeList(String fieldName) {
        return goToNode(fieldName).toRootNodeList();
    }

    public <T> List<T> toObjectList(Class<T> aClass) {
        return toRootNodeList()
                .stream()
                .map(rootNode -> rootNode.toObject(aClass))
                .collect(Collectors.toList());
    }

    public <T> List<T> toObjectList(String fieldName, Class<T> aClass) {
        return goToNode(fieldName).toObjectList(aClass);
    }

    public <T> T toObject(String fieldName, Class<T> aClass) {
        Optional<JsonNode> jsonNode = getValue(fieldName);
        return jsonNode.map(node ->
                objectMapper
                        .convertValue(
                                node
                                , aClass
                        )
        ).orElse(null);
    }

    public <T> T toObject(Class<T> aClass) {
        if (transientJsonNode != null)
            return objectMapper
                    .convertValue(
                            transientJsonNode
                            , aClass
                    );
        else
            return objectMapper
                    .convertValue(
                            rootJsonNode
                            , aClass
                    );
    }

    public Optional<JsonNode> getValue() {
        if (transientJsonNode != null) {
            return Optional.of(transientJsonNode);
        } else {
            return Optional.ofNullable(rootJsonNode);
        }
    }

    public Optional<JsonNode> getValue(String fieldName) {
        return goToNode(fieldName).getValue();
    }

    public String getStringValue(String fieldName) {
        return getValue(fieldName)
                .map(this::textValue)
                .map(this::writeValueAsString)
                .orElse(null);
    }

    private String writeValueAsString(String s) {
        try {
            return objectMapper.writeValueAsString(s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getStringValue() {
        return getValue()
                .map(this::textValue)
                .orElse(null);
    }

    private String textValue(JsonNode jsonNode) {
        if (getType(jsonNode).contains(ExportType.TEXT))
            return jsonNode.textValue();
        throw new RuntimeException(
                "Cannot invoke \"getStringValue\" because '" + jsonNode + "' not String"
        );
    }

    public BigDecimal getBigDecimalValue(String fieldName) {
        return getValue(fieldName)
                .map(this::asBigDecimal)
                .orElse(null);
    }

    public BigDecimal getBigDecimalValue() {
        return getValue()
                .map(this::asBigDecimal)
                .orElse(null);
    }

    private BigDecimal asBigDecimal(JsonNode jsonNode) {
        if (getType(jsonNode).contains(ExportType.BIG_DECIMAL)) {
            return jsonNode.decimalValue();
        }
        throw new RuntimeException(
                "Cannot invoke \"getDecimalValue\" because '" + jsonNode + "' not BigDecimal"
        );
    }

    public Boolean getBooleanValue(String fieldName) {
        return getValue(fieldName)
                .map(JsonNode::booleanValue)
                .orElse(null);
    }

    public Boolean getBooleanValue() {
        return getValue()
                .map(this::booleanValue)
                .orElse(null);
    }

    private Boolean booleanValue(JsonNode jsonNode) {
        if (getType(jsonNode).contains(ExportType.BOOLEAN)) {
            return jsonNode.booleanValue();
        }
        throw new RuntimeException(
                "Cannot invoke \"getBooleanValue\" because '" + jsonNode + "' not Boolean"
        );
    }

    public Integer getIntegerValue(String fieldName) {
        return getValue(fieldName)
                .map(this::intValue)
                .orElse(null);
    }

    public Integer getIntegerValue() {
        return getValue()
                .map(this::intValue)
                .orElse(null);
    }

    private Integer intValue(JsonNode jsonNode) {
        if (getType(jsonNode).contains(ExportType.INTEGER)) {
            return jsonNode.intValue();
        }
        throw new RuntimeException(
                "Cannot invoke \"getIntegerValue\" because '" + jsonNode + "' not Integer"
        );
    }

    public Long getLongValue(String fieldName) {
        return getValue(fieldName)
                .map(this::longValue)
                .orElse(null);
    }

    public Long getLongValue() {
        return getValue()
                .map(this::longValue)
                .orElse(null);
    }

    private Long longValue(JsonNode jsonNode) {
        if (getType(jsonNode).contains(ExportType.LONG)) {
            return jsonNode.longValue();
        }
        throw new RuntimeException(
                "Cannot invoke \"getLongValue\" because '" + jsonNode + "' not Long"
        );
    }


    private List<ExportType> getType(JsonNode jsonNode) {
        ExportType[] result = new ExportType[]{};
        if (jsonNode instanceof ArrayNode)
            result = new ExportType[]{ExportType.ARRAY};
        if (jsonNode instanceof BooleanNode)
            result = new ExportType[]{ExportType.BOOLEAN};
        if (jsonNode instanceof TextNode)
            result = new ExportType[]{ExportType.TEXT};
        if (jsonNode instanceof BigIntegerNode ||
                jsonNode instanceof DecimalNode ||
                jsonNode instanceof DoubleNode ||
                jsonNode instanceof FloatNode
        )
            result = new ExportType[]{ExportType.BIG_DECIMAL};
        if (jsonNode instanceof IntNode || jsonNode instanceof ShortNode)
            result = new ExportType[]{
                    ExportType.INTEGER,
                    ExportType.LONG,
                    ExportType.BIG_DECIMAL
            };
        if (jsonNode instanceof LongNode)
            result = new ExportType[]{
                    ExportType.LONG,
                    ExportType.BIG_DECIMAL
            };
        return Arrays.stream(result).collect(Collectors.toList());
    }
}
