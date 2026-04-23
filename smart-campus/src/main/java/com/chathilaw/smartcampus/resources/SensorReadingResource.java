/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources; // Defines the package for JAX-RS resources

import com.chathilaw.smartcampus.database.MockDatabase; // Imports the MockDatabase for data storage access
import com.chathilaw.smartcampus.exception.SensorUnavailableException; // Imports custom exception for maintenance state errors
import com.chathilaw.smartcampus.model.Sensor; // Imports the Sensor model class
import com.chathilaw.smartcampus.model.SensorReading; // Imports the SensorReading model class

import java.util.ArrayList; // Imports ArrayList for list instantiations
import java.util.List; // Imports List interface
import javax.ws.rs.Consumes; // Imports the Consumes annotation for request payloads
import javax.ws.rs.GET; // Imports the GET HTTP method annotation
import javax.ws.rs.POST; // Imports the POST HTTP method annotation
import javax.ws.rs.Produces; // Imports the Produces annotation for response formats
import javax.ws.rs.core.MediaType; // Imports media type constants
import javax.ws.rs.core.Response; // Imports the JAX-RS Response builder class

/**
 * Sub-resource handling operations for a specific sensor's readings.
 * 
 * @author Chathila Wijesinghe
 */
public class SensorReadingResource { // Note: No @Path here, as this is instantiated by SensorResource (Sub-Resource Locator pattern)

    private String sensorId; // Holds the ID of the parent sensor from the URI path

    public SensorReadingResource() {} // Default constructor required by JAX-RS

    public SensorReadingResource(String sensorId) { // Parameterized constructor used by the parent resource locator
        this.sensorId = sensorId; // Initializes the specific sensor ID context for this sub-resource
    }

    @GET // Maps this method to HTTP GET requests for fetching reading history
    @Produces(MediaType.APPLICATION_JSON) // Ensures the returned list is serialized as JSON
    public Response getReadings() { // Method to retrieve all readings for the specified sensor
        // Ensure the sensor exists in the database
        if (!MockDatabase.getSensors().containsKey(sensorId)) { // Checks if the sensorId is valid
            return Response.status(Response.Status.NOT_FOUND) // Builds a 404 Not Found response
                    .entity("Sensor with ID " + sensorId + " not found.") // Adds an error message to the response body
                    .build(); // Finalizes the Response object
        }

        // Fetch the readings list, returning an empty list if no readings exist yet
        List<SensorReading> readings = MockDatabase.getSensorReadings().getOrDefault(sensorId, new ArrayList<>()); // Retrieves data from the mock DB
        return Response.ok(readings).build(); // Returns an HTTP 200 OK response with the readings list as the JSON body
    }

    @POST // Maps this method to HTTP POST requests for adding new reading entries
    @Consumes(MediaType.APPLICATION_JSON) // Expects the incoming request body to be formatted as JSON
    @Produces(MediaType.APPLICATION_JSON) // Returns the created reading object as JSON in the response
    public Response addReading(SensorReading reading) { // Method to record a new sensor reading
        if (reading == null) { // Validates that the request payload was successfully parsed
            return Response.status(Response.Status.BAD_REQUEST) // Returns HTTP 400 Bad Request
                    .entity("Invalid reading data.") // Provides an error reason
                    .build(); // Finalizes the response
        }

        Sensor sensor = MockDatabase.getSensors().get(sensorId); // Retrieves the parent sensor object to check its state
        
        // Ensure the sensor exists before adding a reading
        if (sensor == null) { // Validation check for an invalid URI sensor ID
            return Response.status(Response.Status.NOT_FOUND) // Returns HTTP 404 Not Found
                    .entity("Sensor with ID " + sensorId + " not found.") // Error message for missing sensor
                    .build(); // Finalizes the response
        }
        
        // Check for State Constraint (403 Forbidden)
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) { // Validates if the sensor is permitted to accept data
            throw new SensorUnavailableException("Sensor is currently under maintenance and cannot accept new readings."); // Triggers the custom exception mapper
        }

        // Add to history
        List<SensorReading> history = MockDatabase.getSensorReadings() // Access the readings map in the database
                .computeIfAbsent(sensorId, k -> new ArrayList<>()); // Gets existing list or creates a new one if it's the first reading
        history.add(reading); // Appends the new reading to the sensor's history
        
        // Side Effect: Trigger an update to the currentValue field on the parent Sensor object
        sensor.setCurrentValue(reading.getValue()); // Keeps the sensor's aggregate view up-to-date with the latest data

        return Response.status(Response.Status.CREATED).entity(reading).build(); // Returns HTTP 201 Created and echoes the saved reading
    }
}
