/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

import com.chathilaw.smartcampus.model.ErrorMessage; // Imports the standard error representation
import javax.ws.rs.core.Response; // Imports the response configuration class
import javax.ws.rs.ext.ExceptionMapper; // Imports the ExceptionMapper interface
import javax.ws.rs.ext.Provider; // Imports the JAX-RS component registration annotation

/**
 * Intercepts SensorUnavailableException and maps it to an HTTP 403 Forbidden response.
 * 
 * @author Chathila Wijesinghe
 */
@Provider // This annotation registers the mapper with JAX-RS for automatic dependency injection
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override // Overrides the contract defined in ExceptionMapper
    public Response toResponse(SensorUnavailableException exception) {
        ErrorMessage errorMessage = new ErrorMessage( // Creates the structured error payload
                exception.getMessage(), // Extracts the reason message from the exception instance
                403, // HTTP 403 Forbidden status code (the server understands but refuses to authorize the state change)
                "https://smartcampus.edu/api/docs/errors" // Generic link to the API docs
        );

        return Response.status(Response.Status.FORBIDDEN) // Sets the HTTP 403 response status
                .entity(errorMessage) // Attaches the JSON error object
                .build(); // Finalizes and returns the response back to the client
    }
}
