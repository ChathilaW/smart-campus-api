/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

import com.chathilaw.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Chathila Wijesinghe
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        // Log the actual exception internally so developers can debug the issue
        System.err.println("Unexpected API Error: " + exception.getMessage());
        exception.printStackTrace(); 

        ErrorMessage errorMessage = new ErrorMessage(
                "An unexpected internal server error occurred. Please contact support if the issue persists.",
                500, // Internal Server Error
                "https://smartcampus.edu/api/docs/errors"
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}
