package DBWriters.Readers;

import DBWriters.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Structures.Pg_station;


import java.math.BigDecimal;
import java.sql.*;

public final class Pg_stationTableReader {
    private Pg_stationTableReader() {
    }
    private static String selectInfoFromPg_stationTable = "SELECT s.IDstation, s.IDcashDesk, s.name, s.deleted, s.in_multiplier, s.out_multiplier, s.bet_multiplier, s.win_multiplier, s.game_multiplier, s.date_last_in, s.last_in_value, s.last_out_value, b.name FROM pg_station s INNER JOIN pg_cash_desk cd on s.IDcashDesk = cd.IDcashDesk INNER JOIN pg_bar b on cd.IDbar = b.IDbar;";
    private static String selectInfoFromPg_stationTableNew = "SELECT s.IDstation, any_value(s.IDcashDesk) AS IDcashDesk, any_value(s.name) AS name, any_value(s.deleted) AS deleted, max(sm.current_in) AS current_in, any_value(sm.current_out) AS current_out, any_value(sm.current_bet) AS current_bet, any_value(sm.current_win) AS current_win, any_value(sm.current_game) AS current_game, any_value(s.date_last_in) AS date_last_in, any_value(b.name) AS bar_name FROM pg_station s INNER JOIN pg_cash_desk cd on s.IDcashDesk = cd.IDcashDesk INNER JOIN pg_settlement_machine sm on s.IDstation = sm.IDstation INNER JOIN pg_bar b on cd.IDbar = b.IDbar group by sm.IDstation;";

    public static ObservableList<Pg_station> read() throws SQLException {
        DB.setUp();
        ObservableList<Pg_station> data = FXCollections.observableArrayList();
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(selectInfoFromPg_stationTableNew)) {
                    System.out.println("Asserting: " + selectInfoFromPg_stationTableNew);                                                                                                                          //TODO: change into currently               change into currently                   change into currently                  change into currently                  change into currently                                                                                 change into currently in - currently out
                    while (resultSet.next()) {                           //     IDstation                       IDcashDesk                          name                            deleted                                 current_in                              current_out                            current_bet                             current_win                            current_game                        date_last_in                                         last_in_value                            last_out_value                         pag_bar.name
                        Pg_station pgStation = new Pg_station(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getBigDecimal(5), resultSet.getBigDecimal(6), resultSet.getBigDecimal(7), resultSet.getBigDecimal(8), resultSet.getBigDecimal(9), resultSet.getTimestamp(10), checkIfNull(resultSet.getBigDecimal(5), resultSet.getBigDecimal(6)), resultSet.getString(11));
                        data.add(pgStation);                             //Integer idStation                Integer idCashDesk                   String name                     Integer deleted                      BigDecimal inMultiplier                  BigDecimal outMultiplier               BigDecimal betMultiplier                 BigDecimal winMultiplier               BigDecimal gameMultiplier               Timestamp dateLastIn                                              BigDecimal availableBalance
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
