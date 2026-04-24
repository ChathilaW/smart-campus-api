/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

import com.chathilaw.smartcampus.model.ErrorMessage; // Imports the common ErrorMessage model
import javax.ws.rs.core.Response; // Imports the Response builder class
import javax.ws.rs.ext.ExceptionMapper; // Imports the mapper interface
import javax.ws.rs.ext.Provider; // Imports the provider annotation

/**
 * Intercepts RoomNotEmptyException and maps it to an HTTP 409 Conflict response.
 * 
 * @author Chathila Wijesinghe
 */
@Provider // This annotation registers the mapper with JAX-RS to be discovered during scanning
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override // Specifies implementation of the interface method
    public Response toResponse(RoomNotEmptyException exception) {
        ErrorMessage errorMessage = new ErrorMessage( // Instantiates the response body object
                exception.getMessage(), // Passes the specific exception message
                409, // HTTP 409 Conflict status code indicating a business logic violation
                "https://smartcampus.edu/api/docs/errors" // Documentation reference link
        );

        return Response.status(Response.Status.CONFLICT) // Configures the response with HTTP status 409
                .entity(errorMessage) // Sets the response body
                .build(); // Completes the response construction
    }
}
