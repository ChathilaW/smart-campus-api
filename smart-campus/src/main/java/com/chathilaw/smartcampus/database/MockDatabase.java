/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.database;

import com.chathilaw.smartcampus.model.Room; // Imports the Room domain model
import com.chathilaw.smartcampus.model.Sensor; // Imports the Sensor domain model
import com.chathilaw.smartcampus.model.SensorReading; // Imports the SensorReading domain model

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An in-memory, singleton-like simulation of a database to store Rooms, Sensors, and Readings during execution.
 * 
 * @author Chathila Wijesinghe
 */
public class MockDatabase {
    private static Map<String, Room> rooms = new HashMap<>(); // A static HashMap acting as the "rooms" table, keyed by room ID

    private static Map<String, Sensor> sensors = new HashMap<>(); // A static HashMap acting as the "sensors" table, keyed by sensor ID
    
    // Map of sensorId -> List of SensorReadings
    private static Map<String, List<SensorReading>> sensorReadings = new HashMap<>(); // A static HashMap mapping a sensor ID to its chronological list of readings

    static { // Static initialization block that runs exactly once when the class is loaded into the JVM
        // Pre-populating with some realistic mock data to make testing easier
        Room r1 = new Room("LIB-1LA", "Library", 20, new ArrayList<>()); // Creates a mock room object representing the Library
        Room r2 = new Room("LAB-2LB", "Computer Lab 2LB", 40, new ArrayList<>()); // Creates a mock room object representing a Lab
        Room r3 = new Room("HALL-2LA", "Lecture Hall 2LA", 120, new ArrayList<>()); // Creates a mock room object representing a Lecture Hall
        Room r4 = new Room("HALL-3LA", "Lecture Hall 3LA", 120, new ArrayList<>()); // Creates a mock room object representing another Lecture Hall
        rooms.put(r1.getId(), r1);
        rooms.put(r2.getId(), r2);
        rooms.put(r3.getId(), r3);
        rooms.put(r4.getId(), r4);
    }

    // Getter for the rooms simulated table
    public static Map<String, Room> getRooms() {
        return rooms; // Returns a reference to the global rooms map
    }
    
    // Getter for the sensors simulated table
    public static Map<String, Sensor> getSensors() {
        return sensors; // Returns a reference to the global sensors map
    }
    
    // Getter for the sensor readings simulated table
    public static Map<String, List<SensorReading>> getSensorReadings() {
        return sensorReadings; // Returns a reference to the global sensor readings map
    }
}
