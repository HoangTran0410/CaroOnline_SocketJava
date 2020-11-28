/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.util.ArrayList;
import shared.helper.RandomString;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class RoomManager {

    ArrayList<Room> rooms;
    RandomString idGenerator;

    public RoomManager() {
        rooms = new ArrayList<>();
        idGenerator = new RandomString(5);
    }

    public Room createRoom() {
        Room room = new Room(idGenerator.nextString());
        rooms.add(room);

        return room;
    }

    public boolean add(Room r) {
        if (!rooms.contains(r)) {
            rooms.add(r);
            return true;
        }
        return true;
    }

    public boolean remove(Room r) {
        if (rooms.contains(r)) {
            rooms.remove(r);
            return true;
        }
        return false;
    }

    public Room find(String id) {
        for (Room r : rooms) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public int getSize() {
        return rooms.size();
    }

}
