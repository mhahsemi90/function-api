/**
 * The {@code org.example.functionapi} module defines the core API for dynamic function execution
 * and JSON data processing in a modular Java application.
 *
 * <p>This module provides the foundational interfaces and classes for creating, executing,
 * and managing dynamic functions that can process JSON data and produce structured results.</p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Dynamic function execution interface</li>
 *   <li>JSON navigation and value extraction utilities</li>
 *   <li>Reactive web client support for HTTP operations</li>
 *   <li>Modular function loading and versioning support</li>
 * </ul>
 *
 * <p><b>Module Dependencies:</b></p>
 * <ul>
 *   <li>{@code com.fasterxml.jackson.databind} - JSON processing and data binding</li>
 *   <li>{@code spring.webflux} - Reactive web support for Spring Framework</li>
 *   <li>{@code reactor.core} - Reactive programming foundation</li>
 *   <li>{@code spring.web} - Spring Web MVC support</li>
 * </ul>
 *
 * <p><b>Exported Packages:</b></p>
 * <ul>
 *   <li>{@code org.example.functionapi} - Core API interfaces and classes</li>
 * </ul>
 *
 * <p><b>Typical Usage:</b></p>
 * <pre>
 * // Load function modules dynamically
 * Function function = loadFunctionModule();
 * // Execute with input data
 * FunctionResult result = function.execute(functionDto, elementInputMap, sharedInputMap);
 * </pre>
 *
 * @see org.example.functionapi.Function
 * @see org.example.functionapi.FunctionDto
 * @see org.example.functionapi.FunctionResult
 * @see org.example.functionapi.RootNode
 */
module org.example.functionapi {
    requires com.fasterxml.jackson.databind;
    requires spring.webflux;
    requires reactor.core;
    requires spring.web;
    exports org.example.functionapi;
}