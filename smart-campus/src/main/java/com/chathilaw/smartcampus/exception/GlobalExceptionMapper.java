/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

import com.chathilaw.smartcampus.model.ErrorMessage; // Imports the ErrorMessage model for standardized error responses
import javax.ws.rs.core.Response; // Imports the JAX-RS Response class
import javax.ws.rs.ext.ExceptionMapper; // Imports the interface for mapping exceptions to responses
import javax.ws.rs.ext.Provider; // Imports the annotation to register this class as a JAX-RS provider

/**
 * Catches all unhandled exceptions (Throwable) to prevent raw stack traces from reaching the client.
 * 
 * @author Chathila Wijesinghe
 */
@Provider // Registers this class with the JAX-RS runtime as an extension provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> { // Implements the mapper for the root Throwable class

    @Override
    public Response toResponse(Throwable exception) { // Method called automatically when an unhandled Throwable occurs
        // Log the actual exception internally so developers can debug the issue
        System.err.println("Unexpected API Error: " + exception.getMessage()); // Logs the error message to standard error
        exception.printStackTrace(); // Prints the full stack trace for internal debugging

        ErrorMessage errorMessage = new ErrorMessage( // Constructs a standardized ErrorMessage object
                "An unexpected internal server error occurred. Please contact support if the issue persists.", // Generic, safe message for the client
                500, // HTTP 500 Internal Server Error status code
                "https://smartcampus.edu/api/docs/errors" // Link to API error documentation
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // Sets the HTTP response status to 500
                .type(javax.ws.rs.core.MediaType.APPLICATION_JSON) // Explicitly sets the response Content-Type to application/json
                .entity(errorMessage) // Embeds the ErrorMessage object into the response body
                .build(); // Builds and returns the final Response object
    }
}
