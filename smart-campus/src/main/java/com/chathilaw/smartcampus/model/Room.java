/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chathila Wijesinghe
 */
public class Room {
    private String id; // Unique identifier, e.g., "LIB-301"
    private String name; // Human-readable name, e.g., "Library Quiet Study"
    private int capacity; // Maximum occupancy for safety regulations
    private List<String> sensorIds = new ArrayList<>(); // Collection of IDs of sensors deployed in this room
    
    //constructors
    public Room(){}
    public Room(String id, String name, int capacity, List<String> sensorIds){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIds = new ArrayList<>(sensorIds);
    }
    
    //getters
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getCapacity(){
        return capacity;
    }
    public List<String> getSensorIds() {
        return sensorIds;
    }
    
    //setters
    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = new ArrayList<>(sensorIds);
    }
}
