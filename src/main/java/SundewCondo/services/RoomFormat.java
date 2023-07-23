package SundewCondo.services;

import SundewCondo.models.Room;

import java.util.List;

public class RoomFormat implements Format {
    @Override
    public <T> void getFormat(List<String> a, List<T> b) {
        for(String e : a) {
            String[] data = e.split(",");
            b.add((T) new Room(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5]));
        }
    }
}
