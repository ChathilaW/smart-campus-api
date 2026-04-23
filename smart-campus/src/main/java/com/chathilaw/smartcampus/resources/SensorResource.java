/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources; // Declares the package for JAX-RS resources

import com.chathilaw.smartcampus.database.MockDatabase; // Imports the simulated database class
import com.chathilaw.smartcampus.exception.LinkedResourceNotFoundException; // Imports custom exception for invalid foreign keys
import com.chathilaw.smartcampus.model.Room; // Imports the Room model
import com.chathilaw.smartcampus.model.Sensor; // Imports the Sensor model

import java.util.List; // Imports List interface
import java.util.Map; // Imports Map interface
import java.util.stream.Collectors; // Imports stream collectors for filtering lists
import javax.ws.rs.Consumes; // Annotates methods that consume specific request media types
import javax.ws.rs.GET; // Annotates methods that handle HTTP GET requests
import javax.ws.rs.POST; // Annotates methods that handle HTTP POST requests
import javax.ws.rs.Path; // Defines the base URI path for this resource class or method
import javax.ws.rs.PathParam; // Binds URI template parameters to method arguments
import javax.ws.rs.Produces; // Annotates methods that produce specific response media types
import javax.ws.rs.QueryParam; // Binds URI query parameters to method arguments
import javax.ws.rs.core.MediaType; // Provides media type string constants (e.g., application/json)
import javax.ws.rs.core.Response; // Represents an HTTP response with status codes and entities

/**
 * Resource class managing operations on the /sensors endpoint collection.
 * 
 * @author Chathila Wijesinghe
 */
@Path("/sensors") // Sets the base path for all endpoints in this class to /api/v1/sensors
public class SensorResource { // Public class declaration for the Sensor REST endpoints

    private Map<String, Sensor> sensors = MockDatabase.getSensors(); // Retrieves the in-memory map of sensors
    private Map<String, Room> rooms = MockDatabase.getRooms(); // Retrieves the in-memory map of rooms for validation

    @POST // Maps this method to HTTP POST requests for creating new sensors
    @Consumes(MediaType.APPLICATION_JSON) // Expects the incoming payload to be JSON
    @Produces(MediaType.APPLICATION_JSON) // Responds with JSON representation of the created sensor
    public Response createSensor(Sensor sensor) { // Endpoint method taking a deserialized Sensor object
        if (sensor == null || sensor.getRoomId() == null || sensor.getId() == null) { // Basic payload validation
            return Response.status(Response.Status.BAD_REQUEST) // Returns 400 Bad Request
                    .entity("Invalid sensor data.") // Error message
                    .build(); // Builds the HTTP response
        }

        // Verify that the roomId exists in the system to maintain referential integrity
        if (!rooms.containsKey(sensor.getRoomId())) { // Checks the rooms database for the provided room ID
            throw new LinkedResourceNotFoundException("Room ID " + sensor.getRoomId() + " does not exist."); // Throws an exception mapped to a 422 Unprocessable Entity
        }

        // Optional: Check if sensor ID already exists to prevent overwriting
        if (sensors.containsKey(sensor.getId())) { // Looks up existing sensor by ID
            return Response.status(Response.Status.CONFLICT) // Returns 409 Conflict status
                    .entity("Sensor with ID " + sensor.getId() + " already exists.") // Conflict error message
                    .build(); // Builds the response
        }

        sensors.put(sensor.getId(), sensor); // Saves the new sensor into the mock database
        
        // Optional: Add sensor ID to the parent room's list of sensor IDs
        Room room = rooms.get(sensor.getRoomId()); // Fetches the parent room
        room.getSensorIds().add(sensor.getId()); // Updates the room's bidirectional relationship with the sensor

        return Response.status(Response.Status.CREATED) // Returns 201 Created status
                .entity(sensor) // Includes the saved sensor in the response body
                .build(); // Builds and returns the response
    }
    
    @GET // Maps this method to HTTP GET requests for retrieving the sensors collection
    @Produces(MediaType.APPLICATION_JSON) // Response format is JSON
    public Response getSensors(@QueryParam("type") String type) { // Method accepts an optional ?type= filter query parameter
        List<Sensor> result; // Local list to hold the filtered or unfiltered results
        
        if (type != null && !type.trim().isEmpty()) { // Checks if a valid filter type was provided
            // Filter sensors by type
            result = sensors.values().stream() // Opens a stream on the sensor collection
                    .filter(sensor -> type.equalsIgnoreCase(sensor.getType())) // Retains only sensors matching the requested type (case-insensitive)
                    .collect(Collectors.toList()); // Collects the stream back into a List
        } else {
            // Return all sensors if no type is provided
            result = sensors.values().stream().collect(Collectors.toList()); // Converts the map values collection to a list
        }

        return Response.ok(result).build(); // Returns HTTP 200 OK with the final list as the JSON entity
    }
    
    @Path("/{sensorId}/readings") // Sub-resource locator for nested readings endpoint (e.g., /sensors/{sensorId}/readings)
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) { // Extracts the path parameter
        // Return a new instance of the sub-resource locator
        return new SensorReadingResource(sensorId); // Delegates request handling to the SensorReadingResource, passing the ID context
    }
}
