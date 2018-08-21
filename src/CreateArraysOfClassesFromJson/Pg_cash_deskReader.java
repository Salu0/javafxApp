package CreateArraysOfClassesFromJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import Structures.Pg_cash_desk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Pg_cash_deskReader {

    public ArrayList<Pg_cash_desk> read(String path) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .serializeNulls()
                .create();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<Pg_cash_desk> pg_cash_desks = gson.fromJson(br, new TypeToken<ArrayList<Pg_cash_desk>>(){}.getType());
            return pg_cash_desks;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
