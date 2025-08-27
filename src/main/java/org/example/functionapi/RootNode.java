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

/**
 * A wrapper class for JSON navigation and value extraction from JsonNode structures.
 * Provides fluent API for traversing JSON nodes and converting them to various Java types.
 * Supports both direct node access and field-based navigation.
 */
public class RootNode {
    private final JsonNode rootJsonNode;
    private final ObjectMapper objectMapper;
    private JsonNode transientJsonNode;

    /**
     * Constructs a RootNode from a JSON string.
     *
     * @param objectJson the JSON string to parse
     * @param mapper     the ObjectMapper for JSON processing
     * @throws JsonProcessingException if the JSON string is invalid
     */
    public RootNode(String objectJson, ObjectMapper mapper) throws JsonProcessingException {
        this.objectMapper = mapper;
        this.rootJsonNode = mapper.readTree(objectJson);
    }

    /**
     * Constructs a RootNode from an existing JsonNode.
     *
     * @param rootJsonNode the JsonNode to wrap
     * @param mapper       the ObjectMapper for JSON processing
     */
    public RootNode(JsonNode rootJsonNode, ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.rootJsonNode = rootJsonNode;
    }

    /**
     * Resets navigation to the root node and returns this RootNode.
     *
     * @return this RootNode instance pointing to the root
     */
    public RootNode getNode() {
        transientJsonNode = null;
        return this;
    }

    /**
     * Navigates to a specific field within the JSON structure.
     *
     * @param fieldName the name of the field to navigate to
     * @return this RootNode instance pointing to the specified field
     * @throws RuntimeException if the field is not found
     */
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

    /**
     * Converts the current node to a list of RootNode objects.
     * Useful for processing JSON arrays.
     *
     * @return a list of RootNode objects representing array elements
     * @throws RuntimeException if the current node is not an array
     */
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

    /**
     * Navigates to a field and converts it to a list of RootNode objects.
     *
     * @param fieldName the name of the array field
     * @return a list of RootNode objects from the specified array field
     */
    public List<RootNode> toRootNodeList(String fieldName) {
        return goToNode(fieldName).toRootNodeList();
    }

    /**
     * Converts the current array node to a list of objects of specified type.
     *
     * @param <T>    the type of objects in the list
     * @param aClass the class of the objects to create
     * @return a list of converted objects
     */
    public <T> List<T> toObjectList(Class<T> aClass) {
        return toRootNodeList()
                .stream()
                .map(rootNode -> rootNode.toObject(aClass))
                .collect(Collectors.toList());
    }

    /**
     * Navigates to a field and converts its array values to a list of objects.
     *
     * @param <T>       the type of objects in the list
     * @param fieldName the name of the array field
     * @param aClass    the class of the objects to create
     * @return a list of converted objects from the specified field
     */
    public <T> List<T> toObjectList(String fieldName, Class<T> aClass) {
        return goToNode(fieldName).toObjectList(aClass);
    }

    /**
     * Navigates to a field and converts it to an object of specified type.
     *
     * @param <T>       the type of the object to create
     * @param fieldName the name of the field to convert
     * @param aClass    the class of the object to create
     * @return the converted object or null if the field doesn't exist
     */
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

    /**
     * Converts the current node to an object of specified type.
     *
     * @param <T>    the type of the object to create
     * @param aClass the class of the object to create
     * @return the converted object
     */
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

    /**
     * Gets the current JsonNode as an Optional.
     *
     * @return Optional containing the current JsonNode
     */
    public Optional<JsonNode> getValue() {
        if (transientJsonNode != null) {
            return Optional.of(transientJsonNode);
        } else {
            return Optional.ofNullable(rootJsonNode);
        }
    }

    /**
     * Gets a field value as an Optional JsonNode.
     *
     * @param fieldName the name of the field to retrieve
     * @return Optional containing the field's JsonNode
     */
    public Optional<JsonNode> getValue(String fieldName) {
        return goToNode(fieldName).getValue();
    }

    /**
     * Gets a string value from a field.
     *
     * @param fieldName the name of the field containing the string value
     * @return the string value or null if the field doesn't exist
     * @throws RuntimeException if the field exists but is not a string
     */
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

    /**
     * Gets the string value of the current node.
     *
     * @return the string value or null if the node doesn't exist
     * @throws RuntimeException if the node exists but is not a string
     */
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

    /**
     * Gets a BigDecimal value from a field.
     *
     * @param fieldName the name of the field containing the numeric value
     * @return the BigDecimal value or null if the field doesn't exist
     * @throws RuntimeException if the field exists but is not a numeric type
     */
    public BigDecimal getBigDecimalValue(String fieldName) {
        return getValue(fieldName)
                .map(this::asBigDecimal)
                .orElse(null);
    }

    /**
     * Gets the BigDecimal value of the current node.
     *
     * @return the BigDecimal value or null if the node doesn't exist
     * @throws RuntimeException if the node exists but is not a numeric type
     */
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

    /**
     * Gets a boolean value from a field.
     *
     * @param fieldName the name of the field containing the boolean value
     * @return the boolean value or null if the field doesn't exist
     * @throws RuntimeException if the field exists but is not a boolean
     */
    public Boolean getBooleanValue(String fieldName) {
        return getValue(fieldName)
                .map(JsonNode::booleanValue)
                .orElse(null);
    }

    /**
     * Gets the boolean value of the current node.
     *
     * @return the boolean value or null if the node doesn't exist
     * @throws RuntimeException if the node exists but is not a boolean
     */
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

    /**
     * Gets an integer value from a field.
     *
     * @param fieldName the name of the field containing the integer value
     * @return the integer value or null if the field doesn't exist
     * @throws RuntimeException if the field exists but is not an integer
     */
    public Integer getIntegerValue(String fieldName) {
        return getValue(fieldName)
                .map(this::intValue)
                .orElse(null);
    }

    /**
     * Gets the integer value of the current node.
     *
     * @return the integer value or null if the node doesn't exist
     * @throws RuntimeException if the node exists but is not an integer
     */
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

    /**
     * Gets a long value from a field.
     *
     * @param fieldName the name of the field containing the long value
     * @return the long value or null if the field doesn't exist
     * @throws RuntimeException if the field exists but is not a long
     */
    public Long getLongValue(String fieldName) {
        return getValue(fieldName)
                .map(this::longValue)
                .orElse(null);
    }

    /**
     * Gets the long value of the current node.
     *
     * @return the long value or null if the node doesn't exist
     * @throws RuntimeException if the node exists but is not a long
     */
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

    /**
     * Determines the export types supported by a JsonNode.
     *
     * @param jsonNode the node to analyze
     * @return a list of supported ExportType values for the node
     */
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