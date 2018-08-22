package CreateArraysOfClassesFromJson;

import Serializers.CustomDateJsonSerializer;
import Structures.Pg_bar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

public class Pg_barReader {

    public ArrayList<Pg_bar> read(String path) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new CustomDateJsonSerializer())
                .serializeNulls()
                .create();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            return gson.fromJson(br, new TypeToken<ArrayList<Pg_bar>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
