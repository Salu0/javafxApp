package Serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Structures.Pg_bar;

import java.util.ArrayList;
import java.util.Date;

public class Pg_barWriter {
    public String write(ArrayList<Pg_bar> bars) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new CustomDateJsonSerializer())
                .serializeNulls()
                .create();

        return gson.toJson(bars);
    }
}