/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.exception;

/**
 * Custom exception thrown when attempting to delete a room that still has sensors assigned to it.
 * 
 * @author Chathila Wijesinghe
 */
public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String message) { // Constructor accepting an error message
        super(message); // Delegates the message to the superclass constructor
    }
}
