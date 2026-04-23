/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.dao;

import com.chathilaw.smartcampus.model.Room;
import com.chathilaw.smartcampus.model.Sensor;
import com.chathilaw.smartcampus.model.SensorReading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chathila Wijesinghe
 */
public class MockDatabase {
    private static Map<String, Room> rooms = new HashMap<>();

    private static Map<String, Sensor> sensors = new HashMap<>();
    
    // Map of sensorId -> List of SensorReadings
    private static Map<String, List<SensorReading>> sensorReadings = new HashMap<>();

    static {
        // Pre-populating with some realistic mock data to make testing easier
        Room r1 = new Room("LIB-301", "Library", 20, new ArrayList<>());
        Room r2 = new Room("LAB-101", "Main Computer Lab", 40, new ArrayList<>());
        Room r3 = new Room("HALL-301", "Lecture Hall 2LA", 120, new ArrayList<>());
        Room r4 = new Room("HALL-101", "Lecture Hall 3LA", 120, new ArrayList<>());
        rooms.put(r1.getId(), r1);
        rooms.put(r2.getId(), r2);
        rooms.put(r3.getId(), r3);
        rooms.put(r4.getId(), r4);
    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }
    
    public static Map<String, Sensor> getSensors() {
        return sensors;
    }
    
    public static Map<String, List<SensorReading>> getSensorReadings() {
        return sensorReadings;
    }
}
