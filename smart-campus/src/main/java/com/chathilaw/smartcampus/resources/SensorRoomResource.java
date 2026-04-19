/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources;

import com.chathilaw.smartcampus.dao.MockDatabase;
import com.chathilaw.smartcampus.model.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Chathila Wijesinghe
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {

    // Connecting to the centralized MockDatabase
    private Map<String, Room> roomDatabase = MockDatabase.getRooms();

    @GET
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(roomDatabase.values());
        return Response.ok(rooms).build();
    }

    @POST
    public Response createRoom(Room newRoom) {
        Map<String, String> responseObj = new HashMap<>();

        if (newRoom == null || newRoom.getId() == null || newRoom.getId().trim().isEmpty()) {
            responseObj.put("error", "Room ID cannot be empty.");
            return Response.status(Response.Status.BAD_REQUEST).entity(responseObj).build();
        }

        if (roomDatabase.containsKey(newRoom.getId())) {
            responseObj.put("error", "Room with ID " + newRoom.getId() + " already exists.");
            return Response.status(Response.Status.CONFLICT).entity(responseObj).build();
        }

        if (newRoom.getSensorIds() == null) {
            newRoom.setSensorIds(new ArrayList<>());
        }

        roomDatabase.put(newRoom.getId(), newRoom);

        return Response.status(Response.Status.CREATED).entity(newRoom).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = roomDatabase.get(roomId);
        if (room == null) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", "Room with ID '" + roomId + "' was not found.");
            return Response.status(Response.Status.NOT_FOUND).entity(responseObj).build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = roomDatabase.get(roomId);
        
        if (room == null) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", "Room with ID '" + roomId + "' was not found.");
            return Response.status(Response.Status.NOT_FOUND).entity(responseObj).build();
        }

        // Business Logic Constraint: Cannot delete if active sensors are assigned
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", "Room still has active sensors assigned. Deletion blocked.");
            
            // TODO: Part 5 Custom Error Response
            // Will update this block to throw/return the custom error structure once Part 5 is reached.
            return Response.status(Response.Status.CONFLICT).entity(responseObj).build();
        }

        // Successful Deletion
        roomDatabase.remove(roomId);
        return Response.noContent().build(); // HTTP 204 No Content
    }
}