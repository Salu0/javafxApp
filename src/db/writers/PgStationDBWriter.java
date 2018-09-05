package db.writers;

import deserializers.*;
import db.*;
import org.junit.BeforeClass;
import structures.PgStation;

import java.sql.SQLException;
import java.util.ArrayList;

public final class PgStationDBWriter {
    private PgStationDBWriter(){}

    public static void writeWithClearing(String path) {
        PgStationReader pgStationReader = new PgStationReader();
        ArrayList<PgStation> pgStations = pgStationReader.read(path); //"/home/kasa/IdeaProjects/jx_application/JSON/pg_station.json"
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_station;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (PgStation station : pgStations) {
            try {
                DBWriter.prepareSql(station.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
