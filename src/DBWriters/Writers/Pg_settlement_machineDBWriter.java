package DBWriters.Writers;

import CreateArraysOfClassesFromJson.*;
import DBWriters.*;
import Structures.Pg_settlement_machine;

import java.sql.SQLException;
import java.util.ArrayList;

public final class Pg_settlement_machineDBWriter {
    private Pg_settlement_machineDBWriter(){}

    public static void write() {
        Pg_settlement_machineReader pg_settlement_machineReader = new Pg_settlement_machineReader();
        ArrayList<Pg_settlement_machine> pg_settlement_machines = pg_settlement_machineReader.read("/home/kasa/IdeaProjects/jx_application/JSON/pg_settlement_machine.json");
        DB.setUp();
        try {
            DBWriter.clean("truncate pg_settlement_machine;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Pg_settlement_machine machine : pg_settlement_machines) {
            try {
                DBWriter.prepareSql(machine.insertQuery());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DB.closePool();
    }
}
