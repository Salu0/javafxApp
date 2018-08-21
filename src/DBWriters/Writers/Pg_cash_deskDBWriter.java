package DBWriters.Writers;

import CreateArraysOfClassesFromJson.*;
import DBWriters.*;
import Structures.Pg_cash_desk;

import java.sql.SQLException;
import java.util.ArrayList;

public final class Pg_cash_deskDBWriter {
    private Pg_cash_deskDBWriter(){}

    public static void write() {
        Pg_cash_deskReader pg_cash_deskReader = new Pg_cash_deskReader();
        ArrayList<Pg_cash_desk> pg_cash_desks = pg_cash_deskReader.read("/home/kasa/IdeaProjects/jx_application/JSON/pg_cash_desk.json");
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_cash_desk;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Pg_cash_desk desk : pg_cash_desks) {
            try {
                DBWriter.prepareSql(desk.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
