package SundewCondo.models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String building; // อาคาร
    private String floor; // ชั้น
    private String roomNumber; // เลขห้อง
    private String roomType; // ประเภท
    private int countResident; // จำนวนคนในห้อง
    private List<String> resident; // ชื่อคน

    public Room(String building, String floor, String roomNumber, String roomType, int countResident, String name) {
        this.building = building;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.resident = new ArrayList<>();
        this.countResident = countResident;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public List<String> getResident() {
        return resident;
    }

    public int getCountResident() { return countResident; }

    public boolean addResident(String name){
        if(this.roomType.equals("1") && getCountResident() + 1 > 2){
            return false;
        }
        if(this.roomType.equals("2") && getCountResident() + 1 > 4){
            return false;
        }
        this.resident.add(name);
        this.countResident = this.resident.size();
        return true;
    }

    public String getRoomName() { return building + ' ' + floor + roomNumber; }

    @Override
    public String toString() {
        return building + ',' + floor + ',' + roomNumber + ',' + roomType + ',' + countResident + ',' + getRoomName() + ',' + resident ;
    }
}
