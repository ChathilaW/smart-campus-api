# smart-campus-api

**University of Westminster**

* **Name: Chathila Wijesinghe**
* **ID: w2121291/20241067**



## Overview

This project is a fully-featured RESTful API built with **JAX-RS (Jersey 2.32)** and deployed on **Apache Tomcat 9**, implementing a Smart Campus infrastructure management system. The API exposes endpoints for managing physical **Rooms** and the **Sensors** deployed within them, including a full historical **Sensor Readings** log. All data is stored in-memory using thread-safe `ConcurrentHashMap` and `ArrayList` data structures — no database is used.

The implementation covers:

* Versioned API entry point with a HATEOAS-style discovery endpoint
* Full CRUD for Rooms and Sensors with referential integrity enforcement
* Sub-resource locator pattern for nested Sensor Readings
* Four custom JAX-RS `ExceptionMapper` implementations (409, 422, 403, 500)
* A global catch-all safety net mapper to prevent stack trace exposure
* A `ContainerRequestFilter` / `ContainerResponseFilter` logging implementation



## Technology Stack

|Component|Technology|
|-|-|
|Language|Java 23|
|Framework|JAX-RS via Jersey 2.32|
|Server|Apache Tomcat 9.0.100|
|Build Tool|Apache Maven|
|JSON|Jackson (jersey-media-json-jackson)|
|IDE|Apache NetBeans 28|

\---


## Project Structure

```

smart-campus-api
│ smart-campus/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── chathilaw/
│   │	     │ 		        └── smartcampus/
│   │       │               ├── config
│   │       │               │     └── JAXRSConfiguration.java
│   │       │           	 ├── database/
│   │       │           	 │   └── MockDatabase.java
│   │       │           	 ├── exception/
│   │       │           	 │   ├── GlobalExceptionMapper.java
│   │       │           	 │   ├── LinkedResourceNotFoundException.java
│   │       │           	 │   ├── LinkedResourceNotFoundExceptionMapper.java
│   │       │           	 │   ├── RoomNotEmptyException.java
│   │       │           	 │   ├── RoomNotEmptyExceptionMapper.java
│   │       │           	 │   ├── SensorUnavailableException.java
│   │       │           	 │   └── SensorUnavailableExceptionMapper.java
│   │       │           	 ├── filter/
│   │       │           	 │   └── LoggingFilter.java
│   │       │           	 ├── model/
│   │       │           	 │   ├── ErrorMessage.java
│   │       │           	 │   ├── Room.java
│   │       │           	 │   ├── Sensor.java
│   │       │           	 │   └── SensorReading.java
│   │       │           	 └── resource/
│   │       │               	  ├── DiscoveryResource.java
│   │       │               	  ├── SensorRoomResource.java
│   │       │               	  ├── SensorReadingResource.java
│   │       │               	  └── SensorResource.java
│   │       │   
│   │       └── webapp/
│   │           ├── META-INF/
│   │           │   └── context.xml
│   │           ├── WEB-INF/
│   │           │   ├── beans.xml
│   │           │   └── web.xml
│   │           └── index.html
│   ├── nb-configuration.xml
│   └── pom.xml 
│
├──.gitignore
└── README.md



```

\\---
Build \& Run Instructions
---


### Prerequisites

* Apache NetBeans 24 or heigher (with JDK 17 or heigher)
* Apache Tomcat Apache Tomcat 9.0.100 added to NetBeans Services tab
* Internet connection (for Maven to download dependencies on first build)

### Step 1 — Clone the repository

```bash
git clone https://github.com/ChathilaW/smart-campus-api.git
```

### Step 2 — Open in NetBeans

1. Open **Apache NetBeans**
2. Go to **File → Open Project**
3. Navigate to the cloned folder and select it
4. Click **Open Project**

### Step 3 — Add Apache Tomcat (if not already added)

1. Go to the **Services** tab
2. Right-click **Servers → Add Server**
3. Select **Apache Tomcat or TomEE**, click **Next**
4. Browse to your extracted Tomcat 9 folder, enter credentials, click **Finish**

### Step 4 — Build

Right-click the project root → **Clean and Build**. Wait for `BUILD SUCCESS` in the output window.

### Step 5 — Run

Right-click the project root → **Run**. The server starts and deploys the WAR automatically.

### Step 6 — Verify

Open a browser or Postman and navigate to:

```
http://localhost:8080/smart-campus/api/v1/
```

You should receive a JSON discovery response confirming the API is live.

\---

## API Base URL

```
http://localhost:8080/smart-campus/api/v1
```
\---

## API Endpoints Reference

### Part 1 — Discovery

|Method|Endpoint|Description|
|-|-|-|
|GET|`/api/v1/`|Returns API metadata, version, and resource map|

### Part 2 — Rooms

|Method|Endpoint|Description|Success Code|
|-|-|-|-|
|GET|`/api/v1/rooms`|List all rooms|200|
|POST|`/api/v1/rooms`|Create a new room|201 Created|
|GET|`/api/v1/rooms/{roomId}`|Get a specific room by ID|200|
|DELETE|`/api/v1/rooms/{roomId}`|Delete a room (blocked if sensors present)|204|

### Part 3 — Sensors

|Method|Endpoint|Description|Success Code|
|-|-|-|-|
|GET|`/api/v1/sensors`|List all sensors (optional?type= filter)|200|
|POST|`/api/v1/sensors`|Register a new sensor (validates roomId)|201 Created|
|GET|`/api/v1/sensors/{sensorId}`|Get a specific sensor|200|

### Part 4 — Sensor Readings (Sub-Resource)

|Method|Endpoint|Description|Success Code|
|-|-|-|-|
|GET|`/api/v1/sensors/{sensorId}/readings`|Get full reading history|200|
|POST|`/api/v1/sensors/{sensorId}/readings`|Post a new reading (updates parent sensor)|201|

### Part 5 — Error Responses

|Scenario|HTTP Status|Exception Class|
|-|-|-|
|Delete room with active sensors|409 Conflict|`RoomNotEmptyException`|
|POST sensor with non-existent roomId|422 Unprocessable Entity|`LinkedResourceNotFoundException`|
|POST reading to MAINTENANCE sensor|403 Forbidden|`SensorUnavailableException`|
|Any unexpected runtime error|500 Internal Server Error|`GlobalExceptionMapper<Throwable>`|
|GET non-existent resource|404 Not Found|JAX-RS `NotFoundException` (via GlobalMapper)|

\---

## Seed Data (Pre-loaded on startup)

**Rooms:**

* `LIB-1LA` — Library (capacity: 20)
* `LAB-2LB` — Computer Lab 2LB (capacity: 40)
* `HALL-2LA` — Lecture Hall 2LA (capacity: 120)
* `HALL-3LA` — Lecture Hall 3LA (capacity: 120)

\---


## Sample curl Commands

```bash
# 1. Discovery endpoint
curl -X GET http://localhost:8080/smart-campus/api/v1/

# 2. List all rooms
curl -X GET http://localhost:8080/smart-campus/api/v1/rooms

# 3. Create a new room (POST)
curl -X POST http://localhost:8080/smart-campus/api/v1/rooms \\\\
  -H "Content-Type: application/json" \\\\
  -d '{"id":"AUD","name":"Auditorium","capacity":600}'

# 4. Filter sensors by type
curl -X GET "http://localhost:8080/smart-campus/api/v1/sensors?type=Temperature"

# 5. Post a sensor reading (updates parent sensor's currentValue)
curl -X POST http://localhost:8080/smart-campus/api/v1/sensors/LIB-1LA/readings \\\\
  -H "Content-Type: application/json" \\\\
  -d '{"id":"RD-001","timestamp":"7200","value":26.3}'

# 6. Trigger 409 Conflict — attempt to delete a room that has sensors
curl -X DELETE http://localhost:8080/smart-campus/api/v1/rooms/LIB-1LA

# 7. Trigger 422 Unprocessable Entity — sensor referencing non-existent room
curl -X POST http://localhost:8080/smart-campus/api/v1/sensors \\\\
  -H "Content-Type: application/json" \\\\
  -d '{"id":"BAD-001","type":"CO2","status":"ACTIVE","currentValue":0.0,"roomId":"FAKE-999"}'

# 8. Trigger 403 Forbidden — post reading to a MAINTENANCE sensor
curl -X POST http://localhost:8080/smart-campus/api/v1/sensors/OCC-001/readings \\\\
  -H "Content-Type: application/json" \\\\
  -d '{"value":15.0}'

# 9. Trigger 500 Internal Server Error — global safety net demonstration
curl -X GET "http://localhost:8080/smart-campus/api/v1/?triggerError=true"

# 10. Delete a room with no sensors
curl -X DELETE http://localhost:8080/smart-campus/api/v1/rooms/HALL-2LA


```

# Report

Answers to the questions posed in each section of the coursework specification.

---

## Part 1.1 - JAX-RS resource lifecycle and its impact on thread-safe data handling.

By default, a JAX-RS resource class follows a per-request lifecycle, meaning the runtime creates a new instance of the resource class for each incoming HTTP request. This approach promotes statelessness and helps ensure thread safety, since each request operates on its own separate object. As a result, any instance variables defined within the resource class are short-lived and are discarded once the request is completed.

Because of this behavior, instance variables cannot be used to persist data across multiple requests. To maintain shared in-memory data such as maps or lists, developers must either declare these structures as static or move them into a shared component, such as a service annotated with @Singleton. This allows the data to exist beyond the lifecycle of a single request.

However, sharing data across requests introduces concurrency concerns, since application servers process multiple requests simultaneously using multiple threads. Without proper handling, this can lead to race conditions, data corruption, or inconsistent application state. To avoid these issues, it is important to use thread-safe data structures such as ConcurrentHashMap or CopyOnWriteArrayList, or to apply synchronization mechanisms when performing more complex operations.
In summary, while the per-request lifecycle simplifies thread safety at the resource level, any shared state must be carefully managed to ensure safe concurrent access.

---

## Part 1.2 - Why hypermedia (HATEOAS) is important in REST and how it helps clients compared to static docs.

The provision of Hypermedia, specifically Hypermedia as the Engine of Application State (HATEOAS), is considered a hallmark of advanced RESTful design because it transforms an API into a self-describing, dynamic system. Instead of returning only raw data, a HATEOAS-compliant API includes links within its responses that guide the client on what actions are currently available, based on the resource’s state.

This concept aligns with the highest level (Level 3) of the Richardson Maturity Model, where APIs not only use proper HTTP methods and status codes but also provide navigable links. Unlike lower-level APIs that require clients to have prior knowledge of endpoint structures, HATEOAS eliminates this dependency by embedding interaction details directly in responses.

One of the key benefits of this approach is loose coupling between client and server. In traditional APIs, client developers rely on static documentation and hardcode URL structures into their applications. If the server changes these endpoints, the client can break. With HATEOAS, clients do not construct URLs themselves; instead, they follow links provided in the response (e.g., a rel="cancel" link). This makes the system more flexible and resilient to backend changes.

Additionally, HATEOAS improves dynamic discoverability and state management. Rather than forcing the client to implement logic to determine which actions are valid (such as checking whether an order can be canceled), the server includes only the relevant links for the current state. The client simply reacts to what is present in the response, reducing complexity and duplication of business logic.

Finally, HATEOAS reduces reliance on static documentation, which can often become outdated. Since available actions are communicated directly in each response, the API effectively documents itself in real time. This allows developers to explore and interact with the API more intuitively, similar to navigating a website, without constantly referring to external documentation.

In summary, HATEOAS enhances RESTful APIs by making them more self-descriptive, adaptable, and client-friendly, while reducing tight coupling and minimizing the risks associated with changing backend implementations.

---

## Part 2.1 - IDs vs full objects trade-offs.

When returning a list of rooms, choosing between sending only IDs or full room objects involves a trade-off between network efficiency and client-side workload.

Returning only IDs results in a much smaller payload, which reduces bandwidth usage and improves response times. However, this approach often shifts the burden to the client, which must make additional requests to retrieve the full details for each room. This can lead to the N+1 request problem, where multiple follow-up calls increase latency and overall complexity.

On the other hand, returning full room objects provides all the necessary data in a single response, eliminating the need for additional requests and simplifying client logic. The downside is that the response payload can become large, especially if there are many rooms or each object contains extensive data. This increases bandwidth consumption and may slow down processing on the client side, particularly on less powerful devices.

To balance these concerns, many APIs adopt a middle-ground approach. They return partial or summary representations of each room (such as ID, name, and a link to the full resource), often combined with pagination or query parameters. This allows clients to retrieve only the data they need while avoiding excessive network usage or unnecessary processing.

---

## Part 2.2 - Whether DELETE is idempotent and what happens if it’s repeated.

Yes, the DELETE operation is idempotent in this implementation. Idempotency means that making the same request multiple times results in the same final state on the server, regardless of how many times it is executed.

When the client sends the first DELETE /rooms/{roomId} request (assuming the room exists and meets all conditions), the server locates the room, removes it from the roomDatabase, and returns an HTTP 204 No Content response to indicate successful deletion.
If the client sends the same DELETE request again, the server attempts to retrieve the room but finds that it no longer exists. As a result, the request fails the validation check (room == null), and the server responds with an HTTP 404 Not Found, indicating that the resource is already absent.

Despite the difference in response codes, the operation remains idempotent because the server’s state does not change after the first request. Whether the DELETE request is sent once or multiple times, the final outcome is identical—the room is not present in the system, and no additional side effects occur.

This behavior aligns with RESTful principles, where idempotency is defined by consistent server state rather than identical responses.

---

## Part 3.1 - What happens if a request’s media type doesn’t match @Consumes(JSON) in JAX-RS.

The @Consumes(MediaType.APPLICATION_JSON) annotation in JAX-RS plays a key role in request routing and content negotiation. It tells the JAX-RS runtime that the annotated endpoint is only capable of processing requests with a Content-Type of JSON. During request handling, the runtime checks the incoming request’s Content-Type header and matches it against the @Consumes annotation to determine if the method is suitable.

If a client sends data in a different format (for example, Content-Type: text/plain), the JAX-RS runtime detects that no resource method can handle that media type. As a result, the request is automatically rejected with an HTTP 415 Unsupported Media Type response.

An important implication of this behavior is that the request is blocked before reaching the application logic. The endpoint method (e.g., createSensor) is never executed, which prevents the need for manual validation or error handling for unsupported formats. This built-in mechanism ensures that only correctly formatted data is processed, improving both robustness and maintainability.

---

## Part 3.2 - Why query parameters are preferred over path variables for filtering collections.

Using @QueryParam for filtering aligns with REST principles by clearly separating resource identification from resource modification. In RESTful design, URL paths are intended to identify resources (for example, /api/v1/sensors/123 refers to a specific sensor), whereas query parameters are used to modify how a collection is retrieved—such as filtering, sorting, or pagination—without changing the identity of the resource itself.

Another advantage of query parameters is their flexibility and extensibility. Clients can easily combine multiple filters in any order, such as /api/v1/sensors?type=CO2&status=ACTIVE&limit=10. This approach is much cleaner and more scalable than encoding filters into the path, which can quickly become long, rigid, and difficult to manage. Path-based filtering also introduces ambiguity—for example, different combinations of filters may require multiple endpoints, increasing complexity and the risk of conflicts.

Additionally, query parameters are naturally optional, allowing clients to include only the filters they need. In contrast, path parameters are typically required, which would force the creation of separate endpoints like /api/v1/sensors/type/{type}. This fragments the API design and separates filtering logic from the main collection endpoint.

Overall, using @QueryParam keeps the API clean, consistent, and easy to extend, while maintaining a single, unified endpoint for retrieving filtered data.

---

## Part 4.1 - How sub-resource locators improve API structure compared to one large controller.

The use of sub-resource locators in JAX-RS supports a clean and maintainable architecture by promoting separation of concerns and high cohesion. Instead of placing all nested endpoints inside a single SensorResource class, extracting a dedicated SensorReadingResource ensures that each class focuses on a specific responsibility. This prevents the main resource from becoming a “God class” overloaded with unrelated logic such as readings, metadata, and maintenance operations.

Another advantage is the reduction of parameter clutter. By passing the sensorId through the constructor (e.g., new SensorReadingResource(sensorId)), the identifier can be stored as an instance variable. This avoids repeatedly declaring @PathParam("sensorId") in every method, resulting in cleaner and more readable code.

This approach also improves modularity and reusability. Smaller, focused resource classes are easier to test and maintain. They can also be reused in other parts of the application without duplicating logic, which enhances flexibility in API design.

Finally, sub-resource locators improve readability and routing clarity. Instead of navigating through a large controller with complex and deeply nested @Path annotations, the structure of the API is reflected more naturally in the class hierarchy. The main resource acts as a dispatcher that delegates handling to the appropriate sub-resource, making the overall design easier to understand and extend.

---

## Part 5.2 - Why HTTP 422 is more appropriate than 404 for invalid references in a valid request payload

HTTP 422 Unprocessable Entity is often preferred in this scenario because it accurately reflects the nature of the error. A 404 Not Found indicates that the requested URL or endpoint itself does not exist. For example, if a client sends a request to POST /api/v1/sensors, returning a 404 would wrongly suggest that the /sensors endpoint is invalid or unavailable.

In contrast, a 422 response means that the server understands the request and its format, but cannot process it due to semantic issues in the data. The request is syntactically correct (e.g., valid JSON and correct Content-Type), but the content contains invalid or inconsistent information.

In this case, the error occurs because the request references a roomId that does not exist in the system. The server is able to parse the request successfully, but it cannot fulfill it due to this logical inconsistency. Therefore, 422 precisely communicates to the client: “Your request is well-formed, but the data you provided is invalid in this context.”
This makes 422 more informative and appropriate than 404, as it helps the client understand that the issue lies in the request data rather than the endpoint itself.

---

## Part 5.4 - Security risks of exposing Java stack traces and what attackers can learn from them.

From a cybersecurity standpoint, exposing stack traces is a form of information disclosure, where internal system details are unintentionally revealed to clients. While a stack trace may not directly compromise the system, it provides attackers with valuable insight into the backend, making it easier to identify and exploit weaknesses.

Stack traces can reveal details about the technology stack and frameworks being used (such as Jersey or Hibernate), and sometimes even their versions. If outdated components are exposed, attackers can search for known vulnerabilities (CVEs) and target them directly. They may also expose the internal structure of the application, including package names and class organization, which helps attackers understand how the system is designed.
Additionally, stack traces can leak file paths and operating system details, giving clues about the server environment, and in some cases may even include sensitive data such as SQL queries or input values that caused errors.

By implementing a global exception handling mechanism (such as a GlobalExceptionMapper), these internal details are hidden from clients. Instead of exposing raw stack traces, the system returns controlled and user-friendly error responses, significantly reducing the risk of information leakage and making the application more secure.

---

## Part 5.5 - Why JAX-RS filters are better than manual logging in each resource method.

Using JAX-RS filters such as ContainerRequestFilter and ContainerResponseFilter is advantageous because they provide a clean, centralized way to handle cross-cutting concerns like logging, instead of scattering logging statements throughout every resource method.
One key benefit is separation of concerns. Resource classes should focus on business logic and request handling, while logging is an infrastructural concern. By moving logging into filters, the resource code remains clean and easier to understand.

Filters also support the DRY (Don’t Repeat Yourself) principle by eliminating repetitive logging code. Instead of adding Logger.info() statements in every endpoint, the logic is written once and applied globally. This ensures consistency, as all requests and responses are logged uniformly without relying on developers to remember to add logging in each method.

Another advantage is maintainability. If logging requirements change—such as adding client IP addresses, correlation IDs, or modifying log formats—these updates can be made in one place rather than across multiple resource classes.

Finally, filters provide access to a broader request and response context. Through objects like ContainerRequestContext and ContainerResponseContext, they can capture headers, URIs, media types, and status codes at different stages of the request lifecycle, enabling more comprehensive and structured logging.
