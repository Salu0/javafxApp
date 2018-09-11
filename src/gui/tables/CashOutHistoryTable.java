package gui;

import db.readers.DatabaseReader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import structures.PgCashOutHistory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CashOutHistoryTable extends TableView<PgCashOutHistory> {

    private int stationId;

    public CashOutHistoryTable(int stationId) {
        this.stationId = stationId;
        defineColumns();
        repopulate();
    }

    void repopulate() {
        try {
            setItems(DatabaseReader.readDataFromPgCashOutHistory("SELECT * FROM pg_cash_out_history where IDstation = " + stationId + ";"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void defineColumns() {
        TableColumn<PgCashOutHistory, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PgCashOutHistory, Integer> idStation = new TableColumn<>("idStation");
        idStation.setCellValueFactory(new PropertyValueFactory<>("idStation"));

        TableColumn<PgCashOutHistory, Timestamp> dateOfRecord = new TableColumn<>("dateTimeOfRecord");
        dateOfRecord.setCellValueFactory(new PropertyValueFactory<>("dateTimeOfRecord"));

        TableColumn<PgCashOutHistory, BigDecimal> cashOutAmount = new TableColumn<>("cashOutAmount");
        cashOutAmount.setCellValueFactory(new PropertyValueFactory<>("cashOutAmount"));

        TableColumn<PgCashOutHistory, String> comment = new TableColumn<>("comment");
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        getColumns().addAll(id, idStation, dateOfRecord, cashOutAmount, comment);
    }
}
