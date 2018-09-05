package db.writers;

import db.DB;
import deserializers.PgCashDeskReader;
import org.junit.BeforeClass;
import structures.PgCashDesk;
import structures.PgCashOutHistory;

import java.sql.SQLException;
import java.util.ArrayList;

public final class PgCashOutHistoryWriter {

    private PgCashOutHistoryWriter() {}

    public static void writeWithClearing(PgCashOutHistory row) {
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_cash_out_history;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DBWriter.prepareSql(row.insertQuerry());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closePool();
    }

    public static void write(PgCashOutHistory... historyData) {
        DB.setUp();
        for (PgCashOutHistory pgCashOutHistory : historyData) {
            try {
                DBWriter.prepareSql(pgCashOutHistory.insertQuerry());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
