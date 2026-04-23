/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources;

import com.chathilaw.smartcampus.dao.MockDatabase;
import com.chathilaw.smartcampus.exception.SensorUnavailableException;
import com.chathilaw.smartcampus.model.Sensor;
import com.chathilaw.smartcampus.model.SensorReading;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Chathila Wijesinghe
 */
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource() {}

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        // Ensure the sensor exists
        if (!MockDatabase.getSensors().containsKey(sensorId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor with ID " + sensorId + " not found.")
                    .build();
        }

        List<SensorReading> readings = MockDatabase.getSensorReadings().getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(readings).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid reading data.")
                    .build();
        }

        Sensor sensor = MockDatabase.getSensors().get(sensorId);
        
        // Ensure the sensor exists
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor with ID " + sensorId + " not found.")
                    .build();
        }
        
        // Check for State Constraint (403 Forbidden)
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is currently under maintenance and cannot accept new readings.");
        }

        // Add to history
        List<SensorReading> history = MockDatabase.getSensorReadings()
                .computeIfAbsent(sensorId, k -> new ArrayList<>());
        history.add(reading);
        
        // Side Effect: Trigger an update to the currentValue field on the parent Sensor object
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}

