package SundewCondo.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomList {
    private List<Room> rooms;

    public RoomList() {
        this.rooms = new ArrayList<>();
    }

    public void setRooms(List<Room> listRoom) { rooms = listRoom; }

    public Room checkRoomInCondo(String room) {
        for (Room e: rooms) {
            if (e.getRoomName().equals(room)){
                return e;
            }
        }
        return null;
    }

    public boolean createRoom(String building, String floor, String room, String type) {
        String[] buildings = {"A", "B", "C"};
        String[] numbers = {"01","02","03","04","05","06","07","08","09","10"};
        if(!Arrays.asList(buildings).contains(building) || !Arrays.asList(numbers).contains(floor) || !Arrays.asList(numbers).contains(room)){
            return false;
        }else if (type.equals("1") || type.equals("2")){
        rooms.add(new Room(building, floor, room, type, 0, null));
        return true;
        }
        return false;
    }

    public boolean addResidentInCondo(Room room, String name){
        if (room.addResident(name)){
            return true;
        }
        return false;
    }

}
