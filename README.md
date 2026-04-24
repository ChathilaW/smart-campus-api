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



