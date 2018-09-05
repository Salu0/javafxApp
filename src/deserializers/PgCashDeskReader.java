package deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import structures.PgCashDesk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class PgCashDeskReader {

    public ArrayList<PgCashDesk> read(String path) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .serializeNulls()
                .create();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<PgCashDesk> pg_cashDesks = gson.fromJson(br, new TypeToken<ArrayList<PgCashDesk>>(){}.getType());
            return pg_cashDesks;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
