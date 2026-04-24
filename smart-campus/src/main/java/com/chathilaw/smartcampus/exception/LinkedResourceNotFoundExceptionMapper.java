/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

import com.chathilaw.smartcampus.model.ErrorMessage; // Imports the standardized ErrorMessage model
import javax.ws.rs.core.Response; // Imports the JAX-RS Response class
import javax.ws.rs.ext.ExceptionMapper; // Imports the interface for mapping exceptions
import javax.ws.rs.ext.Provider; // Imports the annotation to register this class

/**
 * Intercepts LinkedResourceNotFoundException and maps it to an HTTP 422 Unprocessable Entity response.
 * 
 * @author Chathila Wijesinghe
 */
@Provider // This annotation registers the mapper with JAX-RS runtime automatically
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> { // Implements mapping for this specific exception type

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage( // Creates a new ErrorMessage object
                exception.getMessage(), // Uses the exact message passed when the exception was thrown
                422, // HTTP 422 Unprocessable Entity (client provided valid JSON but invalid logical data)
                "https://smartcampus.edu/api/docs/errors" // Link to documentation
        );

        return Response.status(422) // Sets the HTTP status code to 422
                .entity(errorMessage) // Attaches the ErrorMessage object as the response payload
                .build(); // Builds the final JAX-RS Response
    }
}
