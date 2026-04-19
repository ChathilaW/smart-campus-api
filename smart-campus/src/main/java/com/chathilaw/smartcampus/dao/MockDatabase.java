/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.dao;

import com.chathilaw.smartcampus.model.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chathila Wijesinghe
 */
public class MockDatabase {
    private static Map<String, Room> rooms = new HashMap<>();

    // For add static maps for Sensors and SensorReadings later

    static {
        // Pre-populating with some realistic mock data to make testing easier
        Room r1 = new Room("LIB-301", "Library Quiet Study", 20, new ArrayList<>());
        Room r2 = new Room("LAB-101", "Main Computer Lab", 40, new ArrayList<>());
        rooms.put(r1.getId(), r1);
        rooms.put(r2.getId(), r2);
    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }
}
