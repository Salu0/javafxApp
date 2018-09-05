package deserializers;

import serializers.CustomDateJsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import structures.PgBar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

public class PgBarReader {

    public ArrayList<PgBar> read(String path) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new CustomDateJsonSerializer())
                .serializeNulls()
                .create();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<PgBar> pg_bars = gson.fromJson(br, new TypeToken<ArrayList<PgBar>>(){}.getType());
            return pg_bars;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
