package db.writers;

import deserializers.*;
import db.*;
import structures.PgSettlementMachine;

import java.sql.SQLException;
import java.util.ArrayList;

public final class PgSettlementMachineDBWriter {
    private PgSettlementMachineDBWriter(){}

    public static void writeWithClearing(String path) {
        PgSettlementMachineReader pg_settlementMachineReader = new PgSettlementMachineReader();
        ArrayList<PgSettlementMachine> pg_settlementMachines = pg_settlementMachineReader.read(path); //"/home/kasa/IdeaProjects/jx_application/JSON/pg_settlement_machine.json"
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_settlement_machine;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (PgSettlementMachine machine : pg_settlementMachines) {
            try {
                DBWriter.prepareSql(machine.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }

    public static void write(PgSettlementMachine... machineData) {
        DB.setUp();
        for (PgSettlementMachine pgSettlementMachine : machineData) {
            try {
                DBWriter.prepareSql(pgSettlementMachine.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
