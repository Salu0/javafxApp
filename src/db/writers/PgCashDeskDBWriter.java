package db.writers;

import deserializers.*;
import db.*;
import structures.PgCashDesk;

import java.sql.SQLException;
import java.util.ArrayList;

public final class PgCashDeskDBWriter {
    private PgCashDeskDBWriter(){}

    public static void writeWithClearing(String path) {
        PgCashDeskReader pg_cashDeskReader = new PgCashDeskReader();
        ArrayList<PgCashDesk> pg_cashDesks = pg_cashDeskReader.read(path); //"/home/kasa/IdeaProjects/jx_application/JSON/pg_cash_desk.json"
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_cash_desk;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (PgCashDesk desk : pg_cashDesks) {
            try {
                DBWriter.prepareSql(desk.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
