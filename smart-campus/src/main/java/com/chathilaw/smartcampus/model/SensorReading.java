/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.model;

/**
 *
 * @author Chathila Wijesinghe
 */
public class SensorReading {
    private String id; // Unique reading event ID (UUID recommended)
    private long timestamp; // Epoch time (ms) when the reading was captured
    private double value; // The actual metric value recorded by the hardware
    
    //constructors
    public SensorReading(){}
    
    public SensorReading(String id, long timestamp, double value){
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }
    
    //getters
    public String getId(){
        return id;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public double getValue(){
        return value;
    }
    
    //setters
    public void setId(String id){
        this.id = id;
    }
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }
    public void setValue(double value){
        this.value = value;
    }

}
