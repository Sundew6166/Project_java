package SundewCondo.services;

import SundewCondo.models.Document;
import SundewCondo.models.Mail;
import SundewCondo.models.Parcel;

import java.util.List;

public class PostFormat implements Format{
    @Override
    public <T> void getFormat(List<String> a, List<T> b) {
        for(String e : a) {
            String[] data = e.split(",");
            if (data.length == 14) {
                b.add((T) new Mail(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9],
                        data[10], data[11], Boolean.parseBoolean(data[12]), data[13]));
            } else if (data.length == 15) {
                b.add((T) new Document(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9],
                        data[10], data[11], Boolean.parseBoolean(data[12]), data[13], data[14]));
            } else {
                b.add((T) new Parcel(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9],
                        data[10], data[11], Boolean.parseBoolean(data[12]), data[13], data[14], data[15]));
            }
        }
    }
}
