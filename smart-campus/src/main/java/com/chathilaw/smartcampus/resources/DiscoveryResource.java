/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.resources;

import java.util.HashMap; // Imports HashMap for key-value pair data structures
import java.util.Map; // Imports the Map interface
import javax.ws.rs.GET; // Imports the HTTP GET method annotation
import javax.ws.rs.Path; // Imports the Path annotation to define the URL mapping
import javax.ws.rs.Produces; // Imports the Produces annotation to define response content types
import javax.ws.rs.core.MediaType; // Imports MediaType constants like application/json

/**
 * Root discovery resource that provides API metadata and entry points.
 * 
 * @author Chathila Wijesinghe
 */
@Path("/") // Maps this resource to the base API path (e.g., /api/v1/)
public class DiscoveryResource {

    @GET // Indicates that this method responds to HTTP GET requests
    @Produces(MediaType.APPLICATION_JSON) // Specifies that the response body will be in JSON format
    public Map<String, Object> getApiInfo() { // Method that returns the API's top-level information
        Map<String, Object> response = new HashMap<>(); // Creates a map to hold the API metadata
        response.put("name", "Smart Campus Sensor & Room Managment API"); // Adds the API name to the response
        response.put("version", "v1"); // Adds the API version
        response.put("contact", "admi@smartcampus.local"); // Adds the contact email for support
        
        Map<String, String> links = new HashMap<>(); // Creates a nested map for hypermedia links
        links.put("rooms", "/api/v1/rooms"); // Adds the URI for the rooms resource collection
        links.put("sensors", "/api/v1/sensor"); // Adds the URI for the sensors resource collection
        
        response.put("resources", links); // Embeds the resource links into the main response map
        return response; // Returns the map, which JAX-RS will serialize into a JSON object
    }
}
