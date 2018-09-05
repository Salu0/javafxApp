package db.readers;

import db.DB;
import structures.PgCashOutHistory;
import structures.PgSettlementMachine;
import structures.PgStation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.math.BigDecimal;
import java.sql.*;

public final class DatabaseReader {

    public static ObservableList<PgStation> readDataFromPgStation(String query) throws SQLException {
        DB.setUp();
        ObservableList<PgStation> data = FXCollections.observableArrayList();
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    System.out.println("Asserting: " + query);
                    while (resultSet.next()) {                           //     IDstation                       IDcashDesk                          name                            deleted                                 current_in                              current_out                            current_bet                             current_win                            current_game                        date_last_in                                         last_in_value                            last_out_value                         pag_bar.name
                        PgStation pgStation = new PgStation(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getBigDecimal(5), resultSet.getBigDecimal(6), resultSet.getBigDecimal(7), resultSet.getBigDecimal(8), resultSet.getBigDecimal(9), resultSet.getTimestamp(10), checkIfNull(resultSet.getBigDecimal(5), resultSet.getBigDecimal(6)), resultSet.getString(11));
                        data.add(pgStation);                             //Integer idStation                Integer idCashDesk                   String name                     Integer deleted                      BigDecimal inMultiplier                  BigDecimal outMultiplier               BigDecimal betMultiplier                 BigDecimal winMultiplier               BigDecimal gameMultiplier               Timestamp dateLastIn                                              BigDecimal availableBalance
                    }
                    DB.closePool();
                    return data;
                }
            }
        }
    }

    public static ObservableList<PgSettlementMachine> readDataFromPgSettlementMachine(String query) throws SQLException {
        DB.setUp();
        ObservableList<PgSettlementMachine> data = FXCollections.observableArrayList();
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    System.out.println("Asserting: " + query);
                    while (resultSet.next()) {
                        PgSettlementMachine pgSettlementMachine = new PgSettlementMachine(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getTimestamp(5), resultSet.getBigDecimal(6), resultSet.getBigDecimal(7), resultSet.getBigDecimal(8), resultSet.getBigDecimal(9), resultSet.getBigDecimal(10), resultSet.getBigDecimal(11), resultSet.getBigDecimal(12), resultSet.getBigDecimal(13), resultSet.getBigDecimal(14), resultSet.getBigDecimal(15), resultSet.getBigDecimal(16), resultSet.getBigDecimal(17), resultSet.getBigDecimal(18), resultSet.getBigDecimal(19), resultSet.getBigDecimal(20), resultSet.getBigDecimal(21), resultSet.getBigDecimal(22), resultSet.getBigDecimal(23), resultSet.getBigDecimal(24), resultSet.getBigDecimal(25), resultSet.getBigDecimal(26), resultSet.getBigDecimal(27), resultSet.getBigDecimal(28), resultSet.getBigDecimal(29), resultSet.getBigDecimal(30), resultSet.getBigDecimal(31), resultSet.getInt(32), resultSet.getTimestamp(33), resultSet.getInt(34));
                        data.add(pgSettlementMachine);
                    }
                    DB.closePool();
                    return data;
                }
            }
        }
    }

    public static ObservableList<PgCashOutHistory> readDataFromPgCashOutHistory(String query) throws SQLException {
        DB.setUp();
        ObservableList<PgCashOutHistory> data = FXCollections.observableArrayList();
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    System.out.println("Asserting: " + query);
                    while (resultSet.next()) {
                        PgCashOutHistory pgCashOutHistory = new PgCashOutHistory(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3), resultSet.getBigDecimal(4), resultSet.getString(5));
                        data.add(pgCashOutHistory);
                    }
                    DB.closePool();
                    return data;
                }
            }
        }
    }

    private static BigDecimal checkIfNull(BigDecimal lastIn, BigDecimal lastOut) {
        if (lastIn == null) {
            if (lastOut == null) {
                return null;
            }
            lastIn = new BigDecimal("0.00");
        }
        if (lastOut == null) {
            lastOut = new BigDecimal("0.00");
        }
        return lastIn.subtract(lastOut).equals(new BigDecimal("0.00")) ? null : lastIn.subtract(lastOut);
    }
}
