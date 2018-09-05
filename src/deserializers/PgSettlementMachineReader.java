package deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import structures.PgSettlementMachine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class PgSettlementMachineReader {
    public ArrayList<PgSettlementMachine> read(String path) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .serializeNulls()
                .create();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<PgSettlementMachine> pg_settlementMachines = gson.fromJson(br, new TypeToken<ArrayList<PgSettlementMachine>>(){}.getType());
            return pg_settlementMachines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
