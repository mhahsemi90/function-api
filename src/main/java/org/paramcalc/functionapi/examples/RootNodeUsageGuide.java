package org.paramcalc.functionapi.examples;

import org.paramcalc.functionapi.RootNode;

/**
 * RootNode Usage Guide
 * <p>
 * This class serves as a comprehensive guide for using the {@link RootNode} class.
 * It contains examples for all major features and use cases.
 * </p>
 * <p><b>Note:</b> This class is for documentation purposes only and does not contain
 * executable code. All examples are shown as code snippets.
 * </p>
 *
 * <p><b>Table of Contents:</b></p>
 * <ol>
 *   <li>Creating RootNode</li>
 *   <li>Basic Value Extraction</li>
 *   <li>Navigation Methods</li>
 *   <li>Working with Arrays</li>
 *   <li>Object Conversion</li>
 *   <li>Advanced Examples</li>
 *   <li>Error Handling</li>
 *   <li>Best Practices</li>
 * </ol>
 *
 * @see RootNode
 * @since 1.0.0
 */
public class RootNodeUsageGuide {

    /**
     * <b>1. Creating RootNode</b>
     * <p>
     * <b>1.1 From JSON String</b>
     * <pre>
     * String json = """
     *     {
     *         "name": "Ali Rezaei",
     *         "age": 30,
     *         "salary": 5500.75
     *     }
     *     """;
     *
     * ObjectMapper mapper = new ObjectMapper();
     * RootNode root = new RootNode(json, mapper);
     * </pre>
     *
     * <b>1.2 From Existing JsonNode</b>
     * <pre>
     * JsonNode node = mapper.readTree(json);
     * RootNode root = new RootNode(node, mapper);
     * </pre>
     *
     * <b>1.3 With Custom ObjectMapper</b>
     * <pre>
     * ObjectMapper customMapper = new ObjectMapper()
     *     .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
     *     .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
     *
     * RootNode root = new RootNode(json, customMapper);
     * </pre>
     */
    public void section1_creating_rootnode() {
        // Documentation only
    }

    /**
     * <b>2. Basic Value Extraction</b>
     * <p>
     * <b>2.1 Sample JSON</b>
     * <pre>
     * String json = """
     *     {
     *         "name": "Ali Rezaei",
     *         "age": 30,
     *         "salary": 5500.75,
     *         "isActive": true,
     *         "address": {
     *             "city": "Tehran",
     *             "street": "Azadi",
     *             "postalCode": 12345
     *         }
     *     }
     *     """;
     *
     * RootNode root = new RootNode(json, mapper);
     * </pre>
     *
     * <b>2.2 Get String Value</b>
     * <pre>
     * // From direct field
     * String name = root.getStringValue("name");
     * System.out.println(name); // "Ali Rezaei"
     *
     * // From nested field using dot notation
     * String city = root.getStringValue("address.city");
     * System.out.println(city); // "Tehran"
     *
     * // From current node
     * root.goToNode("name");
     * String directName = root.getStringValue();
     * System.out.println(directName); // "Ali Rezaei"
     *
     * // Field doesn't exist - returns null
     * String nonExistent = root.getStringValue("nonExistent");
     * System.out.println(nonExistent); // null
     * </pre>
     *
     * <b>2.3 Get Integer Value</b>
     * <pre>
     * // From direct field
     * Integer age = root.getIntegerValue("age");
     * System.out.println(age); // 30
     *
     * // From nested field
     * Integer postalCode = root.getIntegerValue("address.postalCode");
     * System.out.println(postalCode); // 12345
     *
     * // From current node
     * root.goToNode("age");
     * Integer directAge = root.getIntegerValue();
     * System.out.println(directAge); // 30
     * </pre>
     *
     * <b>2.4 Get Long Value</b>
     * <pre>
     * Long id = root.getLongValue("id");
     * Long timestamp = root.getLongValue("timestamp");
     *
     * // For large numbers
     * Long bigNumber = root.getLongValue("address.postalCode");
     * System.out.println(bigNumber); // 12345L
     * </pre>
     *
     * <b>2.5 Get BigDecimal Value</b>
     * <pre>
     * BigDecimal salary = root.getBigDecimalValue("salary");
     * System.out.println(salary); // 5500.75
     *
     * // From current node
     * root.goToNode("salary");
     * BigDecimal directSalary = root.getBigDecimalValue();
     * System.out.println(directSalary); // 5500.75
     *
     * // For calculations
     * BigDecimal tax = salary.multiply(new BigDecimal("0.1"));
     * System.out.println(tax); // 550.075
     * </pre>
     *
     * <b>2.6 Get Boolean Value</b>
     * <pre>
     * Boolean isActive = root.getBooleanValue("isActive");
     * System.out.println(isActive); // true
     *
     * // From current node
     * root.goToNode("isActive");
     * Boolean directIsActive = root.getBooleanValue();
     * System.out.println(directIsActive); // true
     *
     * // Using in conditions
     * if (isActive) {
     *     System.out.println("User is active");
     * }
     * </pre>
     *
     * <b>2.7 Get Raw JsonNode</b>
     * <pre>
     * // Get as Optional
     * Optional&lt;JsonNode&gt; nodeOpt = root.getValue("address");
     * if (nodeOpt.isPresent()) {
     *     JsonNode addressNode = nodeOpt.get();
     *     String city = addressNode.get("city").asText();
     *     System.out.println(city); // "Tehran"
     * }
     *
     * // Get current node
     * root.goToNode("address");
     * Optional&lt;JsonNode&gt; currentNode = root.getValue();
     * </pre>
     */
    public void section2_basic_value_extraction() {
        // Documentation only
    }

    /**
     * <b>3. Navigation Methods</b>
     * <p>
     * <b>3.1 Sample JSON</b>
     * <pre>
     * String json = """
     *     {
     *         "user": {
     *             "name": "Ali Rezaei",
     *             "address": {
     *                 "city": "Tehran",
     *                 "street": "Azadi",
     *                 "postalCode": 12345
     *             },
     *             "projects": [
     *                 {"name": "Project A", "budget": 1000},
     *                 {"name": "Project B", "budget": 2000}
     *             ]
     *         }
     *     }
     *     """;
     *
     * RootNode root = new RootNode(json, mapper);
     * </pre>
     *
     * <b>3.2 goToNode - Navigate to Field</b>
     * <pre>
     * // Single step navigation
     * root.goToNode("user");
     * String name = root.getStringValue("name");
     * System.out.println(name); // "Ali Rezaei"
     *
     * // Multi-step navigation
     * String city = root.goToNode("user")
     *                  .goToNode("address")
     *                  .getStringValue("city");
     * System.out.println(city); // "Tehran"
     *
     * // Chained navigation with final value
     * String street = root.goToNode("user")
     *                  .goToNode("address")
     *                  .goToNode("street")
     *                  .getStringValue();
     * System.out.println(street); // "Azadi"
     * </pre>
     *
     * <b>3.3 getNode - Reset to Root</b>
     * <pre>
     * // Navigate to nested node
     * root.goToNode("user").goToNode("address");
     *
     * // Do some operations
     * String city = root.getStringValue("city");
     *
     * // Reset to root
     * root.getNode();
     *
     * // Now accessing root fields
     * String userName = root.getStringValue("user.name");
     * System.out.println(userName); // "Ali Rezaei"
     * </pre>
     *
     * <b>3.4 Navigation with Error Handling</b>
     * <pre>
     * try {
     *     root.goToNode("nonExistentField");
     * } catch (RuntimeException e) {
     *     System.out.println("Field not found: " + e.getMessage());
     * }
     *
     * // Safe navigation using Optional
     * Optional&lt;JsonNode&gt; nodeOpt = root.getValue("user.address");
     * if (nodeOpt.isPresent()) {
     *     String city = nodeOpt.get().get("city").asText();
     *     System.out.println(city);
     * }
     * </pre>
     */
    public void section3_navigation_methods() {
        // Documentation only
    }

    /**
     * <b>4. Working with Arrays</b>
     * <p>
     * <b>4.1 Sample JSON with Arrays</b>
     * <pre>
     * String json = """
     *     {
     *         "projects": [
     *             {"name": "Project A", "budget": 1000},
     *             {"name": "Project B", "budget": 2000},
     *             {"name": "Project C", "budget": 3000}
     *         ],
     *         "hobbies": ["Reading", "Swimming", "Programming"]
     *     }
     *     """;
     *
     * RootNode root = new RootNode(json, mapper);
     * </pre>
     *
     * <b>4.2 toRootNodeList - As RootNode List</b>
     * <pre>
     * // Convert array to list of RootNodes
     * List&lt;RootNode&gt; projectNodes = root.toRootNodeList("projects");
     *
     * // Access each item
     * for (RootNode project : projectNodes) {
     *     String name = project.getStringValue("name");
     *     Integer budget = project.getIntegerValue("budget");
     *     System.out.println(name + ": " + budget);
     * }
     *
     * // Using streams
     * BigDecimal totalBudget = projectNodes.stream()
     *     .map(p -> p.getBigDecimalValue("budget"))
     *     .reduce(BigDecimal.ZERO, BigDecimal::add);
     * System.out.println("Total: " + totalBudget); // 6000
     *
     * // From current node
     * root.goToNode("projects");
     * List&lt;RootNode&gt; projects = root.toRootNodeList();
     * </pre>
     *
     * <b>4.3 toObjectList - As Java Objects</b>
     * <pre>
     * // Define your POJO
     * public class Project {
     *     private String name;
     *     private int budget;
     *     // getters and setters
     * }
     *
     * // Convert to list of objects
     * List&lt;Project&gt; projects = root.toObjectList("projects", Project.class);
     *
     * // Use the objects
     * for (Project project : projects) {
     *     System.out.println(project.getName() + ": " + project.getBudget());
     * }
     *
     * // Stream operations
     * List&lt;String&gt; projectNames = projects.stream()
     *     .map(Project::getName)
     *     .collect(Collectors.toList());
     *
     * // From current node
     * root.goToNode("projects");
     * List&lt;Project&gt; projects2 = root.toObjectList(Project.class);
     * </pre>
     *
     * <b>4.4 Working with Primitive Arrays</b>
     * <pre>
     * // String array
     * List&lt;String&gt; hobbies = root
     *     .toRootNodeList("hobbies")
     *     .stream()
     *     .map(RootNode::getStringValue)
     *     .collect(Collectors.toList());
     *
     * System.out.println(hobbies); // [Reading, Swimming, Programming]
     * </pre>
     */
    public void section4_working_with_arrays() {
        // Documentation only
    }

    /**
     * <b>5. Object Conversion</b>
     * <p>
     * <b>5.1 Sample JSON</b>
     * <pre>
     * String json = """
     *     {
     *         "id": 1,
     *         "name": "Ali Rezaei",
     *         "age": 30,
     *         "address": {
     *             "city": "Tehran",
     *             "street": "Azadi",
     *             "postalCode": 12345
     *         },
     *         "projects": [
     *             {"name": "Project A", "budget": 1000}
     *         ]
     *     }
     *     """;
     *
     * RootNode root = new RootNode(json, mapper);
     * </pre>
     *
     * <b>5.2 Define POJOs</b>
     * <pre>
     * public class User {
     *     private int id;
     *     private String name;
     *     private int age;
     *     private Address address;
     *     private List&lt;Project&gt; projects;
     *     // getters and setters
     * }
     *
     * public class Address {
     *     private String city;
     *     private String street;
     *     private int postalCode;
     *     // getters and setters
     * }
     *
     * public class Project {
     *     private String name;
     *     private int budget;
     *     // getters and setters
     * }
     * </pre>
     *
     * <b>5.3 toObject - Convert Field to Object</b>
     * <pre>
     * // Convert address field to Address object
     * Address address = root.toObject("address", Address.class);
     * System.out.println(address.getCity()); // "Tehran"
     * System.out.println(address.getStreet()); // "Azadi"
     *
     * // Convert projects array to list
     * List&lt;Project&gt; projects = root.toObjectList("projects", Project.class);
     * </pre>
     *
     * <b>5.4 toObject - Convert Current Node</b>
     * <pre>
     * // Navigate to a node and convert
     * root.goToNode("address");
     * Address address = root.toObject(Address.class);
     *
     * // Convert entire JSON to object
     * User user = root.toObject(User.class);
     * System.out.println(user.getName()); // "Ali Rezaei"
     * System.out.println(user.getAddress().getCity()); // "Tehran"
     * </pre>
     *
     * <b>5.5 toObject with Null Safety</b>
     * <pre>
     * // Field exists - returns object
     * Address address = root.toObject("address", Address.class);
     *
     * // Field doesn't exist - returns null
     * Address nonExistent = root.toObject("nonExistent", Address.class);
     * if (nonExistent == null) {
     *     System.out.println("Address not found");
     * }
     * </pre>
     */
    public void section5_object_conversion() {
        // Documentation only
    }

    /**
     * <b>6. Advanced Examples</b>
     * <p>
     * <b>6.1 Complete JSON Processing</b>
     * <pre>
     * String json = """
     *     {
     *         "company": {
     *             "name": "Modern Technology",
     *             "employees": [
     *                 {
     *                     "id": 1,
     *                     "name": "Reza Karimi",
     *                     "position": "Manager",
     *                     "salary": 15000.50
     *                 },
     *                 {
     *                     "id": 2,
     *                     "name": "Sara Ahmadi",
     *                     "position": "Developer",
     *                     "salary": 12000.75
     *                 },
     *                 {
     *                     "id": 3,
     *                     "name": "Mehdi Hosseini",
     *                     "position": "Designer",
     *                     "salary": 11000.25
     *                 }
     *             ],
     *             "departments": ["Technical", "Financial", "Marketing"],
     *             "location": {
     *                 "city": "Isfahan",
     *                 "address": "Main Street"
     *             }
     *         }
     *     }
     *     """;
     *
     * RootNode root = new RootNode(json, mapper);
     *
     * // 1. Get company information
     * String companyName = root.goToNode("company").getStringValue("name");
     * System.out.println("Company: " + companyName);
     *
     * // 2. Get all employees
     * List&lt;Employee&gt; employees = root.toObjectList("company.employees", Employee.class);
     * System.out.println("Employees: " + employees.size());
     *
     * // 3. Find highest paid employee
     * Employee highestPaid = employees.stream()
     *     .max(Comparator.comparing(Employee::getSalary))
     *     .orElse(null);
     * System.out.println("Highest paid: " + highestPaid.getName());
     *
     * // 4. Calculate total salary
     * BigDecimal totalSalary = employees.stream()
     *     .map(Employee::getSalary)
     *     .reduce(BigDecimal.ZERO, BigDecimal::add);
     * System.out.println("Total salary: " + totalSalary);
     *
     * // 5. Get departments
     * List&lt;String&gt; departments = root
     *     .goToNode("company")
     *     .toRootNodeList("departments")
     *     .stream()
     *     .map(RootNode::getStringValue)
     *     .collect(Collectors.toList());
     * System.out.println("Departments: " + departments);
     *
     * // 6. Get location
     * String city = root
     *     .goToNode("company")
     *     .goToNode("location")
     *     .getStringValue("city");
     * System.out.println("City: " + city);
     * </pre>
     *
     * <b>6.2 Complex Data Processing</b>
     * <pre>
     * // Filter employees with salary > 12000
     * List&lt;Employee&gt; highPaid = employees.stream()
     *     .filter(e -> e.getSalary().doubleValue() > 12000)
     *     .collect(Collectors.toList());
     *
     * // Group by position
     * Map&lt;String, List&lt;Employee&gt;&gt; byPosition = employees.stream()
     *     .collect(Collectors.groupingBy(Employee::getPosition));
     *
     * // Calculate average salary
     * double averageSalary = employees.stream()
     *     .mapToDouble(e -> e.getSalary().doubleValue())
     *     .average()
     *     .orElse(0.0);
     * </pre>
     *
     * <b>6.3 Nested Array Processing</b>
     * <pre>
     * // JSON with nested arrays
     * String nestedJson = """
     *     {
     *         "departments": [
     *             {
     *                 "name": "IT",
     *                 "employees": [
     *                     {"name": "John", "salary": 5000},
     *                     {"name": "Jane", "salary": 6000}
     *                 ]
     *             },
     *             {
     *                 "name": "HR",
     *                 "employees": [
     *                     {"name": "Bob", "salary": 4000}
     *                 ]
     *             }
     *         ]
     *     }
     *     """;
     *
     * RootNode root = new RootNode(nestedJson, mapper);
     *
     * // Get all employees from all departments
     * List&lt;Employee&gt; allEmployees = root
     *     .toRootNodeList("departments")
     *     .stream()
     *     .flatMap(dept -> dept.toObjectList("employees", Employee.class).stream())
     *     .collect(Collectors.toList());
     * </pre>
     */
    public void section6_advanced_examples() {
        // Documentation only
    }

    /**
     * <b>7. Error Handling</b>
     * <p>
     * <b>7.1 Field Not Found - goToNode</b>
     * <pre>
     * try {
     *     root.goToNode("nonExistentField");
     * } catch (RuntimeException e) {
     *     System.out.println("Error: " + e.getMessage());
     *     // Output: Cannot invoke "getNode" because "nonExistentField" not found
     * }
     * </pre>
     *
     * <b>7.2 Wrong Type - Value Extraction</b>
     * <pre>
     * try {
     *     // Trying to get string from numeric field
     *     String value = root.getStringValue("age");
     * } catch (RuntimeException e) {
     *     System.out.println("Error: " + e.getMessage());
     *     // Output: Cannot invoke "getStringValue" because '30' not String
     * }
     *
     * try {
     *     // Trying to get integer from string field
     *     Integer value = root.getIntegerValue("name");
     * } catch (RuntimeException e) {
     *     System.out.println("Error: " + e.getMessage());
     *     // Output: Cannot invoke "getIntegerValue" because '"Ali"' not Integer
     * }
     * </pre>
     *
     * <b>7.3 Not an Array - toRootNodeList</b>
     * <pre>
     * try {
     *     root.toRootNodeList("name"); // "name" is not an array
     * } catch (RuntimeException e) {
     *     System.out.println("Error: " + e.getMessage());
     *     // Output: Cannot invoke "toRootNodeList" because '"Ali"' not Array
     * }
     * </pre>
     *
     * <b>7.4 Safe Navigation Pattern</b>
     * <pre>
     * // Method 1: Check existence with Optional
     * Optional&lt;JsonNode&gt; nodeOpt = root.getValue("address");
     * if (nodeOpt.isPresent()) {
     *     String city = nodeOpt.get().get("city").asText();
     *     System.out.println(city);
     * } else {
     *     System.out.println("Address not found");
     * }
     *
     * // Method 2: Try-catch for critical operations
     * try {
     *     String city = root.goToNode("address").getStringValue("city");
     *     System.out.println(city);
     * } catch (RuntimeException e) {
     *     System.out.println("Failed to get city: " + e.getMessage());
     * }
     *
     * // Method 3: Null-safe extraction
     * String city = root.getStringValue("address.city"); // Returns null if not found
     * if (city == null) {
     *     System.out.println("City not found, using default");
     *     city = "Unknown";
     * }
     * </pre>
     */
    public void section7_error_handling() {
        // Documentation only
    }

    /**
     * <b>8. Best Practices</b>
     * <p>
     * <b>8.1 Method Chaining</b>
     * <pre>
     * // Good: Clean and readable
     * String city = root.goToNode("user")
     *                  .goToNode("address")
     *                  .getStringValue("city");
     *
     * // Bad: Too verbose
     * root.goToNode("user");
     * root.goToNode("address");
     * String city = root.getStringValue("city");
     * </pre>
     *
     * <b>8.2 Using Dot Notation vs Chaining</b>
     * <pre>
     * // Dot notation - good for simple paths
     * String city = root.getStringValue("user.address.city");
     *
     * // Chaining - good for complex operations
     * String city = root.goToNode("user")
     *                  .goToNode("address")
     *                  .getStringValue("city");
     * </pre>
     *
     * <b>8.3 Reset to Root</b>
     * <pre>
     * // Remember to reset when moving between sections
     * root.goToNode("user");
     * String name = root.getStringValue("name");
     *
     * root.getNode(); // Reset before next section
     *
     * String company = root.getStringValue("company");
     * </pre>
     *
     * <b>8.4 Null Safety</b>
     * <pre>
     * // Always check for null on optional values
     * Integer age = root.getIntegerValue("optionalAge");
     * if (age != null) {
     *     System.out.println("Age: " + age);
     * }
     *
     * // Use Optional for safer handling
     * Optional&lt;JsonNode&gt; nodeOpt = root.getValue("optionalField");
     * nodeOpt.ifPresent(node -> {
     *     // Process node
     * });
     * </pre>
     *
     * <b>8.5 Performance Tips</b>
     * <pre>
     * // Reuse ObjectMapper instance
     * private static final ObjectMapper MAPPER = new ObjectMapper();
     *
     * // Convert once and reuse
     * List&lt;Project&gt; projects = root.toObjectList("projects", Project.class);
     * // Use projects multiple times instead of re-converting
     *
     * // Use streams effectively
     * BigDecimal total = root
     *     .toRootNodeList("projects")
     *     .stream()
     *     .map(p -> p.getBigDecimalValue("budget"))
     *     .reduce(BigDecimal.ZERO, BigDecimal::add);
     * </pre>
     *
     * <b>8.6 Complete Example with Best Practices</b>
     * <pre>
     * public class UserService {
     *     private static final ObjectMapper MAPPER = new ObjectMapper();
     *
     *     public User processUserData(String json) throws JsonProcessingException {
     *         RootNode root = new RootNode(json, MAPPER);
     *
     *         // Extract basic info safely
     *         User user = root.toObject(User.class);
     *         if (user == null) {
     *             throw new IllegalArgumentException("Invalid user data");
     *         }
     *
     *         // Get optional fields with null safety
     *         String middleName = root.getStringValue("middleName");
     *         if (middleName != null) {
     *             user.setMiddleName(middleName);
     *         }
     *
     *         // Process nested data
     *         Address address = root.toObject("address", Address.class);
     *         if (address != null) {
     *             user.setAddress(address);
     *         }
     *
     *         // Process list with error handling
     *         try {
     *             List&lt;Project&gt; projects = root.toObjectList("projects", Project.class);
     *             user.setProjects(projects);
     *         } catch (RuntimeException e) {
     *             user.setProjects(new ArrayList&lt;&gt;());
     *         }
     *
     *         return user;
     *     }
     * }
     * </pre>
     */
    public void section8_best_practices() {
        // Documentation only
    }

    /**
     * <b>Summary of All Methods</b>
     * <br>
     * <br>
     * <table border="1" style="border-collapse: collapse; width: 100%;">
     *   <caption>RootNode Methods Summary</caption>
     *   <thead>
     *     <tr style="background-color: #f0f0f0;">
     *       <th>Method</th>
     *       <th>Description</th>
     *       <th>Parameters</th>
     *       <th>Return Type</th>
     *     </tr>
     *   </thead>
     *   <tbody>
     *     <tr>
     *       <td><code>goToNode(String)</code></td>
     *       <td>Navigate to a field</td>
     *       <td><code>fieldName</code></td>
     *       <td><code>RootNode</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getNode()</code></td>
     *       <td>Reset to root node</td>
     *       <td>None</td>
     *       <td><code>RootNode</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>toRootNodeList()</code></td>
     *       <td>Convert array to RootNode list</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>List&lt;RootNode&gt;</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>toObjectList(Class)</code></td>
     *       <td>Convert array to object list</td>
     *       <td>None or <code>fieldName, Class</code></td>
     *       <td><code>List&lt;T&gt;</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>toObject(Class)</code></td>
     *       <td>Convert node to object</td>
     *       <td>None or <code>fieldName, Class</code></td>
     *       <td><code>T</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getValue()</code></td>
     *       <td>Get JsonNode as Optional</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>Optional&lt;JsonNode&gt;</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getStringValue()</code></td>
     *       <td>Get string value</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>String</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getIntegerValue()</code></td>
     *       <td>Get integer value</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>Integer</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getLongValue()</code></td>
     *       <td>Get long value</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>Long</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getBigDecimalValue()</code></td>
     *       <td>Get BigDecimal value</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>BigDecimal</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>getBooleanValue()</code></td>
     *       <td>Get boolean value</td>
     *       <td>None or <code>fieldName</code></td>
     *       <td><code>Boolean</code></td>
     *     </tr>
     *   </tbody>
     * </table>
     */
    public void summary() {
        // Documentation only
    }
}