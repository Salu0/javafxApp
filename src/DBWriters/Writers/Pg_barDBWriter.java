package DBWriters.Writers;

import CreateArraysOfClassesFromJson.*;
import DBWriters.*;
import Structures.Pg_bar;


import java.sql.SQLException;
import java.util.ArrayList;

public final class Pg_barDBWriter {
    private Pg_barDBWriter() {}

    public static void write() {
        Pg_barReader pg_barReader = new Pg_barReader();
        ArrayList<Pg_bar> pg_bars = pg_barReader.read("/home/kasa/IdeaProjects/jx_application/JSON/pg_bar.json");
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_bar;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Pg_bar bar : pg_bars) {
            try {
                DBWriter.prepareSql(bar.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
