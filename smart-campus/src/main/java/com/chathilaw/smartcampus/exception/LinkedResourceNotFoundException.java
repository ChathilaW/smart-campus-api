/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

/**
 * Custom exception thrown when a linked resource (like a Room referenced by a Sensor) does not exist.
 * 
 * @author Chathila Wijesinghe
 */
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message) { // Constructor taking a custom error message
        super(message); // Passes the message to the parent RuntimeException class
    }
}
