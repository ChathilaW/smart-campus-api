/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources;

import com.chathilaw.smartcampus.dao.MockDatabase;
import com.chathilaw.smartcampus.model.Room;
import com.chathilaw.smartcampus.model.Sensor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Chathila Wijesinghe
 */
@Path("/sensors")
public class SensorResource {

    private Map<String, Sensor> sensors = MockDatabase.getSensors();
    private Map<String, Room> rooms = MockDatabase.getRooms();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (sensor == null || sensor.getRoomId() == null || sensor.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid sensor data.")
                    .build();
        }

        // Verify that the roomId exists in the system
        if (!rooms.containsKey(sensor.getRoomId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID " + sensor.getRoomId() + " does not exist.")
                    .build();
        }

        // Optional: Check if sensor ID already exists
        if (sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Sensor with ID " + sensor.getId() + " already exists.")
                    .build();
        }

        sensors.put(sensor.getId(), sensor);
        
        // Optional: Add sensor ID to the room's list of sensor IDs
        Room room = rooms.get(sensor.getRoomId());
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> result;
        
        if (type != null && !type.trim().isEmpty()) {
            // Filter sensors by type
            result = sensors.values().stream()
                    .filter(sensor -> type.equalsIgnoreCase(sensor.getType()))
                    .collect(Collectors.toList());
        } else {
            // Return all sensors if no type is provided
            result = sensors.values().stream().collect(Collectors.toList());
        }

        return Response.ok(result).build();
    }
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        // Return a new instance of the sub-resource locator
        return new SensorReadingResource(sensorId);
    }
}
