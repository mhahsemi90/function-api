package org.paramcalc.functionapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Data Transfer Object (DTO) that contains the complete definition and metadata of a function.
 * This class is used as the primary input parameter for the {@link Function#execute} method
 * and provides all necessary configuration for function execution, versioning, and dependency management.
 *
 * <p>Instances of this class carry the complete specification of how a function should be
 * executed, including its identity, versioning, execution level, input arguments, and expected outputs.
 * The class supports both traditional setter-based construction and a fluent Builder pattern
 * for convenient object creation.</p>
 *
 * <p><b>Key Concepts:</b></p>
 * <ul>
 *   <li><b>ID:</b> Unique identifier serving as the primary key for the function</li>
 *   <li><b>Function Name:</b> Identifies the specific function implementation to use</li>
 *   <li><b>Version:</b> Allows multiple implementations of the same function</li>
 *   <li><b>Execution Level:</b> Determines execution order in dependency chains</li>
 *   <li><b>Arguments:</b> Function-specific configuration parameters</li>
 *   <li><b>Output Parameters:</b> Expected results that this function produces</li>
 * </ul>
 */
public class FunctionDto {
    private String id;
    private String functionName;
    private String versionFile;
    private Integer functionLevel;
    private Map<String, Object> argumentMap;
    private Set<String> outPutParameterCodeSet;

    /**
     * Default constructor for creating an empty FunctionDto.
     * Useful for frameworks that require no-arg constructors.
     */
    public FunctionDto() {
    }

    /**
     * All-arguments constructor for creating a fully configured FunctionDto.
     *
     * @param id                    the unique identifier of the function
     * @param functionName          the name identifying the function implementation
     * @param versionFile           the version string of the function module
     * @param functionLevel         the execution level for dependency ordering
     * @param argumentMap           the map of function-specific configuration arguments
     * @param outPutParameterCodeSet the set of expected output parameter codes
     */
    public FunctionDto(String id, String functionName, String versionFile, Integer functionLevel,
                       Map<String, Object> argumentMap, Set<String> outPutParameterCodeSet) {
        this.id = id;
        this.functionName = functionName;
        this.versionFile = versionFile;
        this.functionLevel = functionLevel;
        this.argumentMap = argumentMap;
        this.outPutParameterCodeSet = outPutParameterCodeSet;
    }

    /**
     * Gets the unique identifier of the function.
     * This ID serves as the primary key and index for the function within the system.
     *
     * @return the unique string identifier of the function
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the function.
     *
     * @param id the unique string identifier to set for this function
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the human-readable name of the function.
     * This name indicates what specific function implementation should be used and loaded.
     *
     * @return the name of the function
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Sets the human-readable name of the function.
     *
     * @param functionName the name to set for this function
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * Gets the version identifier of the function module file.
     * This allows different versions of the same function to have different implementations
     * and be loaded as separate JAR modules. Different versions can have completely
     * different implementation logic while maintaining the same function name.
     *
     * @return the version string of the function module file
     */
    public String getVersionFile() {
        return versionFile;
    }

    /**
     * Sets the version identifier of the function module file.
     *
     * @param versionFile the version string to set for this function module
     */
    public void setVersionFile(String versionFile) {
        this.versionFile = versionFile;
    }

    /**
     * Gets the execution level of the function.
     * The level determines the execution order when functions have dependencies:
     * <ul>
     *   <li>Lower level numbers execute first (outer functions)</li>
     *   <li>Higher level numbers execute later (inner functions that depend on outer function results)</li>
     *   <li>For example: If function A's output is used in function B, function A (level 1) should execute
     *       before function B (level 2)</li>
     * </ul>
     *
     * @return the execution level as an Integer
     */
    public Integer getFunctionLevel() {
        return functionLevel;
    }

    /**
     * Sets the execution level of the function.
     *
     * @param functionLevel the execution level to set for this function
     */
    public void setFunctionLevel(Integer functionLevel) {
        this.functionLevel = functionLevel;
    }

    /**
     * Gets the map of function-specific arguments and configuration parameters.
     * These arguments are specific to this function instance and provide additional
     * configuration beyond the standard input data. The map uses string keys to identify
     * parameters and object values that can be of any type supported by the function implementation.
     *
     * @return a map where keys are argument names and values are argument values
     */
    public Map<String, Object> getArgumentMap() {
        return argumentMap;
    }

    /**
     * Sets the map of function-specific arguments and configuration parameters.
     *
     * @param argumentMap a map where keys are argument names and values are argument values
     */
    public void setArgumentMap(Map<String, Object> argumentMap) {
        this.argumentMap = argumentMap;
    }

    /**
     * Gets the set of output parameter codes that this function is expected to produce.
     * These codes identify the specific output values that will be available in the
     * {@link FunctionResult} after execution and can be used by dependent functions.
     * The parameter codes should match the keys used in the result maps.
     *
     * @return a set of output parameter code strings
     */
    public Set<String> getOutPutParameterCodeSet() {
        return outPutParameterCodeSet;
    }

    /**
     * Sets the set of output parameter codes that this function is expected to produce.
     *
     * @param outPutParameterCodeSet a set of output parameter code strings
     */
    public void setOutPutParameterCodeSet(Set<String> outPutParameterCodeSet) {
        this.outPutParameterCodeSet = outPutParameterCodeSet;
    }

    /**
     * Creates a new Builder instance for fluent construction of FunctionDto objects.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for fluent construction of FunctionDto instances.
     * Provides a convenient way to create fully configured FunctionDto objects
     * with method chaining and incremental configuration.
     */
    public static class Builder {
        private String id;
        private String functionName;
        private String versionFile;
        private Integer functionLevel;
        private Map<String, Object> argumentMap = new HashMap<>();
        private Set<String> outPutParameterCodeSet = new HashSet<>();

        /**
         * Sets the unique identifier of the function.
         *
         * @param id the unique string identifier
         * @return this Builder instance for method chaining
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the human-readable name of the function.
         *
         * @param functionName the function name
         * @return this Builder instance for method chaining
         */
        public Builder functionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        /**
         * Sets the version identifier of the function module.
         *
         * @param versionFile the version string
         * @return this Builder instance for method chaining
         */
        public Builder versionFile(String versionFile) {
            this.versionFile = versionFile;
            return this;
        }

        /**
         * Sets the execution level of the function.
         *
         * @param functionLevel the execution level
         * @return this Builder instance for method chaining
         */
        public Builder functionLevel(Integer functionLevel) {
            this.functionLevel = functionLevel;
            return this;
        }

        /**
         * Sets the complete argument map for the function.
         * Replaces any previously configured arguments.
         *
         * @param argumentMap the complete map of arguments
         * @return this Builder instance for method chaining
         */
        public Builder argumentMap(Map<String, Object> argumentMap) {
            if (argumentMap != null) {
                this.argumentMap = new HashMap<>(argumentMap);
            }
            return this;
        }

        /**
         * Adds a single argument to the function configuration.
         *
         * @param key   the argument name
         * @param value the argument value
         * @return this Builder instance for method chaining
         */
        public Builder addArgument(String key, Object value) {
            this.argumentMap.put(key, value);
            return this;
        }

        /**
         * Sets the complete set of output parameter codes.
         * Replaces any previously configured output parameters.
         *
         * @param outPutParameterCodeSet the complete set of output parameter codes
         * @return this Builder instance for method chaining
         */
        public Builder outPutParameterCodeSet(Set<String> outPutParameterCodeSet) {
            if (outPutParameterCodeSet != null) {
                this.outPutParameterCodeSet = new HashSet<>(outPutParameterCodeSet);
            }
            return this;
        }

        /**
         * Adds a single output parameter code to the expected outputs.
         *
         * @param parameterCode the output parameter code to add
         * @return this Builder instance for method chaining
         */
        public Builder addOutputParameter(String parameterCode) {
            this.outPutParameterCodeSet.add(parameterCode);
            return this;
        }

        /**
         * Adds multiple output parameter codes to the expected outputs.
         *
         * @param parameterCodes the set of output parameter codes to add
         * @return this Builder instance for method chaining
         */
        public Builder addOutputParameters(Set<String> parameterCodes) {
            this.outPutParameterCodeSet.addAll(parameterCodes);
            return this;
        }

        /**
         * Builds and returns a fully configured FunctionDto instance.
         *
         * @return a new FunctionDto instance with the configured properties
         */
        public FunctionDto build() {
            return new FunctionDto(id, functionName, versionFile, functionLevel,
                    argumentMap, outPutParameterCodeSet);
        }
    }

    /**
     * Returns a string representation of the FunctionDto for debugging and logging purposes.
     *
     * @return a string showing all configured properties of the FunctionDto
     */
    @Override
    public String toString() {
        return "FunctionDto{" +
                "id='" + id + '\'' +
                ", functionName='" + functionName + '\'' +
                ", versionFile='" + versionFile + '\'' +
                ", functionLevel=" + functionLevel +
                ", argumentMap=" + argumentMap +
                ", outPutParameterCodeSet=" + outPutParameterCodeSet +
                '}';
    }
}