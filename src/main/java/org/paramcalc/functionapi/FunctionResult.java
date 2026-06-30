package org.paramcalc.functionapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the output result of a function execution.
 * This class contains both element-specific results and general function output parameters.
 *
 * <p>FunctionResult is returned by the {@link Function#execute} method and carries the processed
 * data organized by element identifiers along with any additional global output parameters.</p>
 *
 * <p><b>Structure Overview:</b></p>
 * <ul>
 *   <li><b>functionResultMap:</b> Element-specific results organized by element ID</li>
 *   <li><b>someParam:</b> Global output parameters applicable to the entire function execution</li>
 * </ul>
 */
public class FunctionResult {
    private Map<Long, Map<String, Object>> functionResultMap;
    private Map<String, Object> someParam;

    /**
     * Default constructor creating empty result maps.
     * Useful for frameworks that require no-arg constructors.
     */
    public FunctionResult() {
    }

    /**
     * All-arguments constructor for creating a fully populated FunctionResult.
     *
     * @param functionResultMap the map containing element-specific results
     * @param someParam         the map containing global output parameters
     */
    public FunctionResult(Map<Long, Map<String, Object>> functionResultMap, Map<String, Object> someParam) {
        this.functionResultMap = functionResultMap;
        this.someParam = someParam;
    }

    /**
     * Gets the element-specific result map.
     *
     * <p>The outer map uses element IDs (Long) as keys, which correspond to the keys
     * from the input {@code elementInputMap} provided to the {@link Function#execute} method.
     * Each element ID maps to an inner map containing the specific output results for that element.</p>
     *
     * <p><b>Inner Map Structure:</b></p>
     * <ul>
     *   <li>Typically contains the function ID from {@link FunctionDto} as one of the keys</li>
     *   <li>May include additional output values beyond the primary function result</li>
     *   <li>Requires careful management in the main application code</li>
     *   <li>Keys should match the expected output parameter codes</li>
     * </ul>
     *
     * @return a nested map where outer keys are element IDs and inner maps contain
     * element-specific result data
     */
    public Map<Long, Map<String, Object>> getFunctionResultMap() {
        return functionResultMap;
    }

    /**
     * Sets the element-specific result map.
     *
     * @param functionResultMap the nested map to set for element-specific results
     */
    public void setFunctionResultMap(Map<Long, Map<String, Object>> functionResultMap) {
        this.functionResultMap = functionResultMap;
    }

    /**
     * Gets the global output parameters map.
     *
     * <p>This map contains output values that are applicable to the entire function execution
     * rather than being specific to individual elements. These parameters represent global
     * results, statistics, or metadata about the function execution that are not tied to
     * particular elements.</p>
     *
     * @return a map containing global output parameters for the function execution
     */
    public Map<String, Object> getSomeParam() {
        return someParam;
    }

    /**
     * Sets the global output parameters map.
     *
     * @param someParam the map to set for global output parameters
     */
    public void setSomeParam(Map<String, Object> someParam) {
        this.someParam = someParam;
    }

    /**
     * Creates a new Builder instance for fluent construction of FunctionResult objects.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for fluent construction of FunctionResult instances.
     * Provides a convenient way to create and configure FunctionResult objects
     * with method chaining and incremental result building.
     */
    public static class Builder {
        private Map<Long, Map<String, Object>> functionResultMap = new HashMap<>();
        private Map<String, Object> someParam = new HashMap<>();

        /**
         * Sets the complete function result map, replacing any existing results.
         *
         * @param functionResultMap the complete nested map of element results
         * @return this Builder instance for method chaining
         */
        public Builder functionResultMap(Map<Long, Map<String, Object>> functionResultMap) {
            if (functionResultMap != null) {
                this.functionResultMap = new HashMap<>(functionResultMap);
            }
            return this;
        }

        /**
         * Adds a complete result entry for a specific element.
         *
         * @param key      the element ID (must match input element IDs)
         * @param valueMap the complete result map for this element
         * @return this Builder instance for method chaining
         */
        public Builder addFunctionResult(Long key, Map<String, Object> valueMap) {
            if (key != null && valueMap != null) {
                this.functionResultMap.put(key, new HashMap<>(valueMap));
            }
            return this;
        }

        /**
         * Adds a single result entry for a specific element and parameter.
         * Creates the element entry if it doesn't exist.
         *
         * @param key        the element ID
         * @param innerKey   the result parameter key (typically function ID or output parameter code)
         * @param innerValue the result value for this parameter
         * @return this Builder instance for method chaining
         */
        public Builder addFunctionResultEntry(Long key, String innerKey, Object innerValue) {
            if (key != null && innerKey != null) {
                this.functionResultMap
                        .computeIfAbsent(key, k -> new HashMap<>())
                        .put(innerKey, innerValue);
            }
            return this;
        }

        /**
         * Sets the complete global parameters map, replacing any existing parameters.
         *
         * @param someParam the complete map of global output parameters
         * @return this Builder instance for method chaining
         */
        public Builder someParam(Map<String, Object> someParam) {
            if (someParam != null) {
                this.someParam = new HashMap<>(someParam);
            }
            return this;
        }

        /**
         * Adds a single global output parameter.
         *
         * @param key   the parameter name
         * @param value the parameter value
         * @return this Builder instance for method chaining
         */
        public Builder addSomeParam(String key, Object value) {
            if (key != null) {
                this.someParam.put(key, value);
            }
            return this;
        }

        /**
         * Adds multiple global output parameters.
         *
         * @param params the map of parameters to add
         * @return this Builder instance for method chaining
         */
        public Builder addSomeParams(Map<String, Object> params) {
            if (params != null) {
                this.someParam.putAll(params);
            }
            return this;
        }

        /**
         * Clears all element-specific results from the builder.
         *
         * @return this Builder instance for method chaining
         */
        public Builder clearFunctionResultMap() {
            this.functionResultMap.clear();
            return this;
        }

        /**
         * Clears all global parameters from the builder.
         *
         * @return this Builder instance for method chaining
         */
        public Builder clearSomeParam() {
            this.someParam.clear();
            return this;
        }

        /**
         * Builds and returns a fully configured FunctionResult instance.
         *
         * @return a new FunctionResult instance with the configured results and parameters
         */
        public FunctionResult build() {
            return new FunctionResult(functionResultMap, someParam);
        }
    }

    /**
     * Returns a string representation of the FunctionResult for debugging and logging purposes.
     * Shows the structure of both element results and global parameters.
     *
     * @return a string representation of the FunctionResult
     */
    @Override
    public String toString() {
        return "FunctionResult{" +
                "functionResultMap=" + functionResultMap +
                ", someParam=" + someParam +
                '}';
    }
}