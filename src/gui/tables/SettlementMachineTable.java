package gui;

import db.readers.DatabaseReader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import structures.PgSettlementMachine;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

class SettlementMachineTable extends TableView<PgSettlementMachine> {

    private int id;

    SettlementMachineTable(int id) {
        this.id = id;
        defineColumns();
        repopulate();
    }

    void repopulate() {
        try {
            setItems(DatabaseReader.readDataFromPgSettlementMachine("SELECT * FROM pg_settlement_machine where IDstation = " + id + ";"));
        } catch (SQLException e) {
            // showerror
            e.printStackTrace();
        }
    }

    private void defineColumns() {
        TableColumn<PgSettlementMachine, Integer> idRecord = new TableColumn<>("idRecord");
        idRecord.setCellValueFactory(new PropertyValueFactory<>("idRecord"));

        TableColumn<PgSettlementMachine, Integer> idPrevRecord = new TableColumn<>("idPrevRecord");
        idPrevRecord.setCellValueFactory(new PropertyValueFactory<>("idPrevRecord"));

        TableColumn<PgSettlementMachine, Integer> idSettlement = new TableColumn<>("idSettlement");
        idSettlement.setCellValueFactory(new PropertyValueFactory<>("idSettlement"));

        TableColumn<PgSettlementMachine, Integer> idStation = new TableColumn<>("idStation");
        idStation.setCellValueFactory(new PropertyValueFactory<>("idStation"));

        TableColumn<PgSettlementMachine, Date> date = new TableColumn<>("date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<PgSettlementMachine, BigDecimal> currentIn = new TableColumn<>("currentIn");
        currentIn.setCellValueFactory(new PropertyValueFactory<>("currentIn"));

        TableColumn<PgSettlementMachine, BigDecimal> currentOut = new TableColumn<>("currentOut");
        currentOut.setCellValueFactory(new PropertyValueFactory<>("currentOut"));

        TableColumn<PgSettlementMachine, BigDecimal> currentBet = new TableColumn<>("currentBet");
        currentBet.setCellValueFactory(new PropertyValueFactory<>("currentBet"));

        TableColumn<PgSettlementMachine, BigDecimal> currentWin = new TableColumn<>("currentWin");
        currentWin.setCellValueFactory(new PropertyValueFactory<>("currentWin"));

        TableColumn<PgSettlementMachine, BigDecimal> currentGame = new TableColumn<>("currentGame");
        currentGame.setCellValueFactory(new PropertyValueFactory<>("currentGame"));

        TableColumn<PgSettlementMachine, BigDecimal> currentTicketIn = new TableColumn<>("currentTicketIn");
        currentTicketIn.setCellValueFactory(new PropertyValueFactory<>("currentTicketIn"));

        TableColumn<PgSettlementMachine, BigDecimal> currentTicketOut = new TableColumn<>("currentTicketOut");
        currentTicketOut.setCellValueFactory(new PropertyValueFactory<>("currentTicketOut"));

        TableColumn<PgSettlementMachine, BigDecimal> currentPromoIn = new TableColumn<>("currentPromoIn");
        currentPromoIn.setCellValueFactory(new PropertyValueFactory<>("currentPromoIn"));

        TableColumn<PgSettlementMachine, BigDecimal> currentPromoOut = new TableColumn<>("currentPromoOut");
        currentPromoOut.setCellValueFactory(new PropertyValueFactory<>("currentPromoOut"));

        TableColumn<PgSettlementMachine, BigDecimal> currentJackpotOut = new TableColumn<>("currentJackpotOut");
        currentJackpotOut.setCellValueFactory(new PropertyValueFactory<>("currentJackpotOut"));

        TableColumn<PgSettlementMachine, BigDecimal> oldIn = new TableColumn<>("oldIn");
        oldIn.setCellValueFactory(new PropertyValueFactory<>("oldIn"));

        TableColumn<PgSettlementMachine, BigDecimal> oldOut = new TableColumn<>("oldOut");
        oldOut.setCellValueFactory(new PropertyValueFactory<>("oldOut"));

        TableColumn<PgSettlementMachine, BigDecimal> newIn = new TableColumn<>("newIn");
        newIn.setCellValueFactory(new PropertyValueFactory<>("newIn"));

        TableColumn<PgSettlementMachine, BigDecimal> newOut = new TableColumn<>("newOut");
        newOut.setCellValueFactory(new PropertyValueFactory<>("newOut"));

        TableColumn<PgSettlementMachine, BigDecimal> totalIn = new TableColumn<>("totalIn");
        totalIn.setCellValueFactory(new PropertyValueFactory<>("totalIn"));

        TableColumn<PgSettlementMachine, BigDecimal> totalOut = new TableColumn<>("totalOut");
        totalOut.setCellValueFactory(new PropertyValueFactory<>("totalOut"));

        TableColumn<PgSettlementMachine, BigDecimal> totalTicketIn = new TableColumn<>("totalTicketIn");
        totalTicketIn.setCellValueFactory(new PropertyValueFactory<>("totalTicketIn"));

        TableColumn<PgSettlementMachine, BigDecimal> totalTicketOut = new TableColumn<>("totalTicketOut");
        totalTicketOut.setCellValueFactory(new PropertyValueFactory<>("totalTicketOut"));

        TableColumn<PgSettlementMachine, BigDecimal> totalPromoIn = new TableColumn<>("totalPromoIn");
        totalPromoIn.setCellValueFactory(new PropertyValueFactory<>("totalPromoIn"));

        TableColumn<PgSettlementMachine, BigDecimal> totalPromoOut = new TableColumn<>("totalPromoOut");
        totalPromoOut.setCellValueFactory(new PropertyValueFactory<>("totalPromoOut"));

        TableColumn<PgSettlementMachine, BigDecimal> totalJackpotOut = new TableColumn<>("totalJackpotOut");
        totalJackpotOut.setCellValueFactory(new PropertyValueFactory<>("totalJackpotOut"));

        TableColumn<PgSettlementMachine, BigDecimal> totalBet = new TableColumn<>("totalBet");
        totalBet.setCellValueFactory(new PropertyValueFactory<>("totalBet"));

        TableColumn<PgSettlementMachine, BigDecimal> totalWin = new TableColumn<>("totalWin");
        totalWin.setCellValueFactory(new PropertyValueFactory<>("totalWin"));

        TableColumn<PgSettlementMachine, BigDecimal> totalGame = new TableColumn<>("totalGame");
        totalGame.setCellValueFactory(new PropertyValueFactory<>("totalGame"));

        TableColumn<PgSettlementMachine, BigDecimal> payoff = new TableColumn<>("payoff");
        payoff.setCellValueFactory(new PropertyValueFactory<>("payoff"));

        TableColumn<PgSettlementMachine, BigDecimal> missingPayoff = new TableColumn<>("missingPayoff");
        missingPayoff.setCellValueFactory(new PropertyValueFactory<>("missingPayoff"));

        TableColumn<PgSettlementMachine, Integer> manualEntry = new TableColumn<>("manualEntry");
        manualEntry.setCellValueFactory(new PropertyValueFactory<>("manualEntry"));

        TableColumn<PgSettlementMachine, Integer> manualInserted = new TableColumn<>("manualInserted");
        manualInserted.setCellValueFactory(new PropertyValueFactory<>("manualInserted"));

        TableColumn<PgSettlementMachine, Timestamp> idUser = new TableColumn<>("idUser");
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        getColumns().addAll(idRecord, idPrevRecord, idSettlement, idStation, date, currentIn, currentOut, currentBet, currentWin, currentGame, currentTicketIn, currentTicketOut, currentPromoIn, currentPromoOut, currentJackpotOut, oldIn, oldOut, newIn, newOut, totalIn, totalOut, totalTicketIn, totalTicketOut, totalPromoIn, totalPromoOut, totalJackpotOut, totalBet, totalWin, totalGame, payoff, missingPayoff, manualEntry, manualInserted, idUser);
    }
}
