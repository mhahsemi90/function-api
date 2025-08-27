package org.example.functionapi;

/**
 * Enum representing the supported data types for JSON node value extraction.
 * Used by {@link RootNode} to identify and validate the type of JSON nodes during conversion operations.
 *
 * <p>This enum helps in type-safe extraction of values from JSON structures and provides
 * clear categorization of supported data types for export operations.</p>
 */
public enum ExportType {
    /**
     * Represents decimal numbers (BigDecimal). Compatible with JSON numeric types
     * including BigIntegerNode, DecimalNode, DoubleNode, and FloatNode.
     */
    BIG_DECIMAL,

    /**
     * Represents string values. Compatible with JSON TextNode.
     */
    TEXT,

    /**
     * Represents long integer values. Compatible with JSON LongNode and integer types
     * that can be promoted to long.
     */
    LONG,

    /**
     * Represents integer values. Compatible with JSON IntNode and ShortNode.
     */
    INTEGER,

    /**
     * Represents boolean values. Compatible with JSON BooleanNode.
     */
    BOOLEAN,

    /**
     * Represents JSON arrays. Compatible with JSON ArrayNode.
     * Used for converting JSON arrays to lists of objects or RootNode instances.
     */
    ARRAY,

    /**
     * Represents undefined or unsupported types.
     * Used when the JSON node type cannot be mapped to any specific export type.
     */
    NOT_DEFINED,
    ;
}