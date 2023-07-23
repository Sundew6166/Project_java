package SundewCondo.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataSource {
    public List<String> read(String name) throws IOException {
        List<String> list = new ArrayList<>();
        String fs = File.separator;
        String dir = System.getProperty("user.dir") + fs + "data";
        String filename = dir + fs + name;
        File fileDir = new File(dir);
        fileDir.mkdirs();
        File file = new File(filename);
        file.createNewFile();

        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        return list;
    }

    public <T> void write(List<T> list, String fileName) throws IOException {
        String fs = File.separator;
        String dir = System.getProperty("user.dir") + fs + "data";
        String filename = dir + fs + fileName;
        File fileDir = new File(dir);
        fileDir.mkdirs();
        File file = new File(filename);
        file.createNewFile();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for(T e : list){
                printWriter.println(e);
            }
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filename);
        }
    }
}
