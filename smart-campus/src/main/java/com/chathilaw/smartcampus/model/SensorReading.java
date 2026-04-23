/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.model;

/**
 * Represents an individual reading captured by a sensor at a specific point in time.
 * 
 * @author Chathila Wijesinghe
 */
public class SensorReading {
    private String id; // Unique identifier for the reading event (UUID recommended)
    private long timestamp; // Epoch time in milliseconds when the reading was captured
    private double value; // The actual metric value recorded by the hardware at the given timestamp
    
    // Default constructor
    public SensorReading(){}
    
    // Parameterized constructor to initialize a new reading
    public SensorReading(String id, long timestamp, double value){
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }
    
    // Getter for the reading ID
    public String getId(){
        return id; // Returns the unique identifier of this reading
    }
    
    // Getter for the timestamp
    public long getTimestamp(){
        return timestamp; // Returns the epoch time of the reading
    }
    
    // Getter for the reading value
    public double getValue(){
        return value; // Returns the recorded metric value
    }
    
    // Setter for the reading ID
    public void setId(String id){
        this.id = id; // Updates the unique identifier of the reading
    }
    
    // Setter for the timestamp
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp; // Updates the time the reading occurred
    }
    
    // Setter for the reading value
    public void setValue(double value){
        this.value = value; // Updates the recorded metric value
    }

}
