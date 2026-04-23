/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.model;

import java.util.ArrayList;
import java.util.List; // Imports the List interface for collections

/**
 * Represents a physical room in the smart campus system.
 * 
 * @author Chathila Wijesinghe
 */
public class Room { 
    private String id; // Unique identifier for the room, e.g., "LIB-1LA"
    private String name; // Human-readable name of the room, e.g., "Library"
    private int capacity; // Maximum occupancy allowed in the room for safety regulations
    private List<String> sensorIds = new ArrayList<>(); // Collection of IDs of sensors deployed in this room
    
    // Default no-argument constructor
    public Room(){} 
    
    // Parameterized constructor to initialize a Room object with specific values
    public Room(String id, String name, int capacity, List<String> sensorIds){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIds = new ArrayList<>(sensorIds);
    }
    
    // Getter for the room ID
    public String getId(){
        return id; // Returns the room's unique identifier
    }
    
    // Getter for the room name
    public String getName(){
        return name; // Returns the room's human-readable name
    }
    
    // Getter for the room capacity
    public int getCapacity(){
        return capacity; // Returns the maximum capacity of the room
    }
    
    // Getter for the list of sensor IDs
    public List<String> getSensorIds() {
        return sensorIds; // Returns the list of sensor IDs associated with this room
    }
    
    // Setter for the room ID
    public void setId(String id){
        this.id = id; // Updates the room's unique identifier
    }
    
    // Setter for the room name
    public void setName(String name){
        this.name = name; // Updates the room's human-readable name
    }
    
    // Setter for the room capacity
    public void setCapacity(int capacity){
        this.capacity = capacity; // Updates the maximum capacity of the room
    }
    
    // Setter for the list of sensor IDs
    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = new ArrayList<>(sensorIds); // Replaces the current sensor list with a new copy of the provided list
    }
}
