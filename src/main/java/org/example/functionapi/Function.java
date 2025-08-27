package org.example.functionapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main interface for function modules that can be dynamically loaded and executed.
 *
 * <p>Implementations of this interface represent executable functions that process input data
 * and produce structured results. These functions are loaded as JAR files at runtime and
 * must follow specific initialization patterns to ensure proper class loading.</p>
 *
 * <p><b>Key Concepts:</b></p>
 * <ul>
 *   <li>Functions receive element-specific input data and shared input data</li>
 *   <li>Functions produce a structured {@link FunctionResult} containing processed data</li>
 *   <li>All classes used within the function must be explicitly declared for proper loading</li>
 *   <li>Functions must handle their own initialization through the provided lifecycle methods</li>
 * </ul>
 */
public interface Function {

    /**
     * Executes the main function logic with the provided input data.
     *
     * @param functionDto       the function definition and metadata containing configuration,
     *                          function name, version, and other operational parameters
     * @param elementInputMap   a map where each key is a unique element identifier (Long)
     *                          and the value is a map of input parameters for that specific element.
     *                          The inner map uses parameter codes as keys and parameter values as values.
     *                          This map may vary for different function implementations.
     * @param shareInputMap     a map of shared input parameters that are accessible across all elements.
     *                          This map remains consistent for all functions and contains common
     *                          configuration or shared data values.
     * @return a {@link FunctionResult} object containing the processed output data organized
     *         by element identifiers and any additional result metadata
     */
    FunctionResult execute(FunctionDto functionDto, Map<Long, Map<String, Object>> elementInputMap, Map<String, Object> shareInputMap);

    /**
     * Returns the set of all classes that may be dynamically instantiated during function execution.
     *
     * <p><b>Critical for Runtime Loading:</b> Since functions are loaded as JAR files at runtime,
     * any class that is instantiated using {@code new} within the {@link #execute} method must be
     * included in this set. This ensures that all required classes are properly loaded and available
     * during execution.</p>
     *
     * @return a {@link Set} of {@link Class} objects representing all classes that might be
     *         dynamically instantiated during function execution. This should include all
     *         concrete classes used within the implementation.
     */
    Set<Class<?>> getClassSet();

    /**
     * Initializes all classes declared in {@link #getClassSet()} by creating instances of them.
     *
     * <p>This default implementation should be called from the constructor of the implementing class
     * to ensure all required classes are pre-loaded and available for runtime execution. This method
     * attempts to create instances using default constructors for all classes in the class set.</p>
     *
     * @throws RuntimeException if any class cannot be instantiated (missing default constructor,
     *         access restrictions, or instantiation errors)
     */
    default void initiate(){
        for (Class<?> aClass : getClassSet()) {
            try {
                aClass.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Provides the list of argument names/keys expected by this function.
     *
     * <p>This default implementation returns an empty list. Implementations should override
     * this method to return the specific parameter codes that this function expects to receive
     * in the element input maps.</p>
     *
     * @return a {@link List} of {@link String} objects representing the expected argument
     *         names/keys for this function. Returns an empty list by default.
     */
    default List<String> argumentList() {
        return new ArrayList<>();
    }

    /**
     * Creates a {@link RootNode} from a JSON object for convenient JSON navigation and value extraction.
     *
     * @param objectJson the JSON object to parse and wrap. Can be a JSON string, Map, or any object
     *                   that can be serialized to JSON format.
     * @return a {@link RootNode} instance providing fluent API for accessing and converting
     *         JSON data values
     * @throws RuntimeException if the object cannot be processed as JSON
     */
    default RootNode getRootNode(Object objectJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new RootNode(objectJson.toString(), mapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serializes a Java object to its JSON string representation.
     *
     * @param value the object to serialize. Can be any Java object serializable by Jackson.
     * @return a JSON string representation of the input object
     * @throws RuntimeException if the object cannot be serialized to JSON
     */
    default String serializeValue(Object value){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}