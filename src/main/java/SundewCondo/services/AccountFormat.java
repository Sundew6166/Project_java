package SundewCondo.services;

import SundewCondo.models.Account;
import java.util.List;

public class AccountFormat implements Format{

    @Override
    public <T> void getFormat(List<String> a, List<T> b) {
        for(String e : a){
            String[] data = e.split(",");
            b.add((T) new Account(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9]));
        }
    }
}
