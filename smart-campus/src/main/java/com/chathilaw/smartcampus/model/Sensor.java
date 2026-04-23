/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.model;

/**
 * Represents a sensor device deployed in a room.
 * 
 * @author Chathila Wijesinghe
 */
public class Sensor {
    private String id; // Unique identifier for the sensor, e.g., "TEMP-001"
    private String type; // Category of the sensor, e.g., "Temperature", "Occupancy", "CO2"
    private String status; // Current operational state: "ACTIVE", "MAINTENANCE", or "OFFLINE"
    private double currentValue; // The most recent measurement value recorded by the sensor
    private String roomId; // Foreign key linking this sensor to the Room where it is located
    
    // Default no-argument constructor
    public Sensor() {} 
    
    // Parameterized constructor to instantiate a Sensor with all properties
    public Sensor(String id, String type, String status, double currentValue, String roomId){
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }
    
    // Getter for the sensor ID
    public String getId(){
        return id; // Retrieves the sensor's unique identifier
    }
    
    // Getter for the sensor type
    public String getType(){
        return type; // Retrieves the category/type of the sensor
    }
    
    // Getter for the sensor status
    public String getStatus(){
        return status; // Retrieves the current operational state of the sensor
    }
    
    // Getter for the current sensor value
    public double getCurrentValue(){
        return currentValue; // Retrieves the most recently recorded value
    }
    
    // Getter for the associated room ID
    public String getRoomId(){
        return roomId; // Retrieves the ID of the room where the sensor is installed
    }
    
    // Setter for the sensor ID
    public void setId(String id){
        this.id = id; // Modifies the sensor's unique identifier
    }
    
    // Setter for the sensor type
    public void setType(String type){
        this.type = type; // Modifies the category/type of the sensor
    }
    
    // Setter for the sensor status
    public void setStatus(String status){
        this.status = status; // Modifies the operational state of the sensor
    }
    
    // Setter for the current sensor value
    public void setCurrentValue(double currentValue){
        this.currentValue = currentValue; // Updates the latest recorded value of the sensor
    }
    
    // Setter for the associated room ID
    public void setRoomId(String roomId){
        this.roomId = roomId; // Changes the room assignment for the sensor
    }
}
