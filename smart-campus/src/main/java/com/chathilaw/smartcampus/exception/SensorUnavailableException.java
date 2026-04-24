/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

/**
 * Custom exception thrown when a sensor is in MAINTENANCE mode and cannot accept data.
 * 
 * @author Chathila Wijesinghe
 */
public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String message) { // Constructor that takes a descriptive string
        super(message); // Passes the descriptive string up to the RuntimeException base class
    }
}
