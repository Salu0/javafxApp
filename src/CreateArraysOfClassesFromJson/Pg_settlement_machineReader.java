package CreateArraysOfClassesFromJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import Structures.Pg_settlement_machine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Pg_settlement_machineReader {
    public ArrayList<Pg_settlement_machine> read(String path) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .serializeNulls()
                .create();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<Pg_settlement_machine> pg_settlement_machines = gson.fromJson(br, new TypeToken<ArrayList<Pg_settlement_machine>>(){}.getType());
            return pg_settlement_machines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
