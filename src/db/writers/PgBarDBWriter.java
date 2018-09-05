package db.writers;

import deserializers.*;
import db.*;
import structures.PgBar;


import java.sql.SQLException;
import java.util.ArrayList;

public final class PgBarDBWriter {
    private PgBarDBWriter() {}

    public static void writeWithClearing(String path) {
        PgBarReader pgBarReader = new PgBarReader();
        ArrayList<PgBar> pgBars = pgBarReader.read(path); //"/home/kasa/IdeaProjects/jx_application/JSON/pg_bar.json"
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_bar;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (PgBar bar : pgBars) {
            try {
                DBWriter.prepareSql(bar.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
