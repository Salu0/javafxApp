package DBWriters.Writers;

import CreateArraysOfClassesFromJson.*;
import DBWriters.*;
import Structures.Pg_station;

import java.sql.SQLException;
import java.util.ArrayList;

public final class Pg_stationDBWriter {
    private Pg_stationDBWriter(){}

    public static void write() {
        Pg_stationReader pg_stationReader = new Pg_stationReader();
        ArrayList<Pg_station> pg_stations = pg_stationReader.read("/home/kasa/IdeaProjects/jx_application/JSON/pg_station.json");
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_station;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Pg_station station : pg_stations) {
            try {
                DBWriter.prepareSql(station.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
