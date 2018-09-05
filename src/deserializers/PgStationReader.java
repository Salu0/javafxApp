package deserializers;

import structures.PgStation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class PgStationReader {
    public ArrayList<PgStation> read(String path) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .serializeNulls()
                .create();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<PgStation> pg_stations = gson.fromJson(br, new TypeToken<ArrayList<PgStation>>(){}.getType());
            return pg_stations;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
