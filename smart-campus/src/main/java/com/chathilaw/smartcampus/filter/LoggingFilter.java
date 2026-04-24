/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.filter;

import java.io.IOException; // Imports the standard exception for IO errors during request processing
import java.util.logging.Logger; // Imports the standard Java logging framework
import javax.ws.rs.container.ContainerRequestContext; // Imports the context class representing an incoming HTTP request
import javax.ws.rs.container.ContainerRequestFilter; // Imports the interface for intercepting incoming requests
import javax.ws.rs.container.ContainerResponseContext; // Imports the context class representing an outgoing HTTP response
import javax.ws.rs.container.ContainerResponseFilter; // Imports the interface for intercepting outgoing responses
import javax.ws.rs.ext.Provider; // Imports the annotation used to register extensions in JAX-RS

/**
 * Filter that intercepts all incoming API requests and outgoing responses to provide observability logs.
 * 
 * @author Chathila Wijesinghe
 */
@Provider // Automatically discovers and registers this filter with the Jersey application
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName()); // Initializes a logger instance specific to this class

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException { // This method is invoked before the request reaches the resource method
        LOGGER.info("--- Incoming Request---"); // Logs a visual separator for clarity
        LOGGER.info("Method: " + requestContext.getMethod()); // Extracts and logs the HTTP method (e.g., GET, POST)
        LOGGER.info("URI: " + requestContext.getUriInfo().getAbsolutePath()); // Extracts and logs the full URL requested by the client
    }

    @Override // Overrides the method from ContainerResponseFilter
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException { // This method is invoked after the resource method executes but before sending the response
        LOGGER.info("--- Outgoing Response---"); // Logs a visual separator for clarity
        LOGGER.info("Status: " + responseContext.getStatus()); // Extracts and logs the HTTP status code being returned
    }
}