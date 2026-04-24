/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources;

import com.chathilaw.smartcampus.database.MockDatabase; // Imports the mocked database singleton
import com.chathilaw.smartcampus.exception.RoomNotEmptyException; // Imports exception for deleting rooms with active sensors
import com.chathilaw.smartcampus.model.Room; // Imports the Room model class

import java.util.ArrayList; // Imports ArrayList implementation
import java.util.HashMap; // Imports HashMap implementation
import java.util.List; // Imports List interface
import java.util.Map; // Imports Map interface
import javax.ws.rs.Consumes; // Annotation for specifying accepted request MIME types
import javax.ws.rs.DELETE; // Annotation for mapping HTTP DELETE requests
import javax.ws.rs.GET; // Annotation for mapping HTTP GET requests
import javax.ws.rs.POST; // Annotation for mapping HTTP POST requests
import javax.ws.rs.Path; // Annotation for binding URIs to resource classes/methods
import javax.ws.rs.PathParam; // Annotation for extracting variables from the URI path
import javax.ws.rs.Produces; // Annotation for specifying returned response MIME types
import javax.ws.rs.core.MediaType; // Class containing standard MIME type constants
import javax.ws.rs.core.Response; // Class for building advanced HTTP responses

/**
 * Resource class managing CRUD operations on the /rooms endpoint collection.
 * 
 * @author Chathila Wijesinghe
 */
@Path("/rooms") // Base URI path for this entire resource class
@Produces(MediaType.APPLICATION_JSON) // Sets a class-level default response format to JSON
@Consumes(MediaType.APPLICATION_JSON) // Sets a class-level default request format to JSON
public class SensorRoomResource {

    // Connecting to the centralized MockDatabase
    private Map<String, Room> roomDatabase = MockDatabase.getRooms(); // Local reference to the simulated rooms table

    @GET // Handles HTTP GET /rooms to fetch the collection
    public Response getAllRooms() { // Method to list all rooms
        List<Room> rooms = new ArrayList<>(roomDatabase.values()); // Converts the map values into a flat list
        return Response.ok(rooms).build(); // Returns 200 OK along with the serialized list of rooms
    }

    @POST // Handles HTTP POST /rooms to create a new entry
    public Response createRoom(Room newRoom) { // Method expecting a JSON payload deserialized into a Room object
        Map<String, String> responseObj = new HashMap<>(); // Reusable map for sending structured JSON error messages

        if (newRoom == null || newRoom.getId() == null || newRoom.getId().trim().isEmpty()) { // Validates that the payload contains a valid ID
            responseObj.put("error", "Room ID cannot be empty."); // Constructs the error message
            return Response.status(Response.Status.BAD_REQUEST).entity(responseObj).build(); // Returns HTTP 400 Bad Request
        }

        if (roomDatabase.containsKey(newRoom.getId())) { // Checks for duplicate IDs to enforce uniqueness constraint
            responseObj.put("error", "Room with ID " + newRoom.getId() + " already exists."); // Constructs the conflict message
            return Response.status(Response.Status.CONFLICT).entity(responseObj).build(); // Returns HTTP 409 Conflict
        }

        if (newRoom.getSensorIds() == null) { // Ensures the list field is never null even if not provided in JSON
            newRoom.setSensorIds(new ArrayList<>()); // Initializes an empty array list
        }

        roomDatabase.put(newRoom.getId(), newRoom); // Persists the new room object into the mock database

        return Response.status(Response.Status.CREATED).entity(newRoom).build(); // Returns HTTP 201 Created and the new resource representation
    }

    @GET // Handles HTTP GET /rooms/{roomId}
    @Path("/{roomId}") // Appends a path parameter template to the base path
    public Response getRoomById(@PathParam("roomId") String roomId) { // Binds the URI segment to the roomId argument
        Room room = roomDatabase.get(roomId); // Attempts to fetch the room from the database
        if (room == null) { // Checks if the room was not found
            Map<String, String> responseObj = new HashMap<>(); // Creates an error response map
            responseObj.put("error", "Room with ID '" + roomId + "' was not found."); // Sets the descriptive error message
            return Response.status(Response.Status.NOT_FOUND).entity(responseObj).build(); // Returns HTTP 404 Not Found
        }
        return Response.ok(room).build(); // Returns HTTP 200 OK with the found room entity
    }

    @DELETE // Handles HTTP DELETE /rooms/{roomId}
    @Path("/{roomId}") // Binds to the specific room ID path
    public Response deleteRoom(@PathParam("roomId") String roomId) { // Deletes the room matching the path parameter
        Room room = roomDatabase.get(roomId); // Retrieves the room to check its current state before deletion
        
        if (room == null) { // Validates that the target resource actually exists
            Map<String, String> responseObj = new HashMap<>(); // Prepares the error format
            responseObj.put("error", "Room with ID '" + roomId + "' was not found."); // Sets the 404 error detail
            return Response.status(Response.Status.NOT_FOUND).entity(responseObj).build(); // Returns 404 Not Found
        }

        // Business Logic Constraint: Cannot delete if active sensors are assigned
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) { // Checks if the room still has sensors linked
            throw new RoomNotEmptyException("The room is currently occupied by active hardware."); // Throws an exception mapped to 409 Conflict
        }

        // Successful Deletion
        roomDatabase.remove(roomId); // Removes the room record from the database
        return Response.noContent().build(); // HTTP 204 No Content indicates success with no response body needed
    }
}