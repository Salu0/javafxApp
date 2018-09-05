package gui;

import db.readers.DatabaseReader;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import structures.PgStation;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ModulesController {

    static SortedList<PgStation>  tableFilter(TextField filterField, ObservableList<PgStation> masterData) {

        FilteredList<PgStation> filteredData = new FilteredList<>(masterData, predicate -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(pg_station -> {
            String lowerCaseFilter = newValue.toLowerCase();
            if (newValue.isEmpty()) { return true; }
            if ((pg_station.getDateLastInAsTimestamp() != null) && (newValue.length() > 2) && ((newValue.charAt(0) == '>') || (newValue.charAt(0) == '<'))) {
                String possibleDate = newValue.substring(1);
                if (newValue.charAt(1) != '=') {
                    try {
                        if ((newValue.charAt(0) == '>') && pg_station.getDateLastInAsTimestamp().after(Timestamp.valueOf(possibleDate))) {
                            return true;
                        } else if ((newValue.charAt(0) == '<') && Timestamp.valueOf(possibleDate).after(pg_station.getDateLastInAsTimestamp())) {
                            return true;
                        }
                    } catch (IllegalArgumentException e1) {
                    }
                }
                if ((newValue.charAt(1) == '=')) {
                    String secondPossibleDate = newValue.substring(2);
                    try { //<=2017-12-19 03:30:20
                        if ((newValue.charAt(0) == '>') && (pg_station.getDateLastInAsTimestamp().after(Timestamp.valueOf(secondPossibleDate)) || pg_station.getDateLastInAsTimestamp().equals(Timestamp.valueOf(secondPossibleDate)))) {
                            return true;
                        } else if ((newValue.charAt(0) == '<') && (pg_station.getDateLastInAsTimestamp().after(Timestamp.valueOf(secondPossibleDate)) || pg_station.getDateLastInAsTimestamp().equals(Timestamp.valueOf(secondPossibleDate)))) {
                            return true;
                        }
                    } catch (IllegalArgumentException e1) {
                    }
                }
            } else if ((pg_station.getName() != null) && pg_station.getName().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if (pg_station.getBarName().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if (pg_station.getIdStation().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if ((pg_station.getInMultiplier() != null) && pg_station.getInMultiplier().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if ((pg_station.getOutMultiplier() != null) && pg_station.getOutMultiplier().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else return (pg_station.getAvailableBalance() != null) && pg_station.getAvailableBalance().toString().toLowerCase().contains(lowerCaseFilter);
            return false;
        }));
        SortedList<PgStation> sortedData = new SortedList<>(filteredData);
        return sortedData;
    }

    static Timestamp getDateSinceInactive() {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        nowTime.setTime(cal.getTime().getTime());
        return nowTime;
    }

    static String calculateDateToShowInTooltip(Timestamp date1) {
        Timestamp date2 = new Timestamp(System.currentTimeMillis());
        Calendar a = getCalendar(date1);
        Calendar b = getCalendar(date2);
        int diffInYears = b.get(YEAR) - a.get(YEAR);
        int diffInMonths = b.get(MONTH) - a.get(MONTH);
        int diffInDays = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);
        int diffInHours = b.get(Calendar.HOUR_OF_DAY) - a.get(Calendar.HOUR_OF_DAY);

        return timeControllerThatIShouldWriteIn2018BecauseTimestampIsFuckedUp(diffInYears,diffInMonths, diffInDays, diffInHours);
    }

    private static String timeControllerThatIShouldWriteIn2018BecauseTimestampIsFuckedUp(int year, int month, int day, int hour) {
        if (hour < 0) {
            hour += 24;
            day -= 1;
        } if (day <= 0) {
            day += 30;
            month -= 1;
        } if (month <= 0) {
            month += 12;
            year -= 1;
        }
        return "Station is inactive " + year + " years, " + month + " months, " + day + " days, " + hour + " hours";
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    static void setOnActionSecondMenuButtons(MenuBar menuBar, PgStation station, BorderPane... panesToChange) {
        MenuItem first = menuBar.getMenus().get(0);
        MenuItem second = menuBar.getMenus().get(1);
        Label firstLabel = (Label)first.getGraphic();
        Label secondLabel = (Label)second.getGraphic();
        if (firstLabel.getText().equals("Statistic")) {
            BorderPane hubPaneBefore = (BorderPane) panesToChange[1].getCenter();
            TableView primaryPaneBefore = (TableView) panesToChange[0].getCenter();
            firstLabel.setOnMouseClicked(event -> {
                panesToChange[1].setCenter(hubPaneBefore);
                panesToChange[0].setCenter(primaryPaneBefore);
            });
            secondLabel.setOnMouseClicked(event -> {
                panesToChange[1].setCenter(null);
                panesToChange[0].setCenter(CreateModules.pgCashOutHistoryTable);
                refreshCashOutTable(station);
            });
        }
    }

    public static void setOnActionFirstMenuButtons(MenuBar menuBar, BorderPane... panesToChange) {
        MenuItem first = menuBar.getMenus().get(0);
        MenuItem second = menuBar.getMenus().get(1);
        MenuItem third = menuBar.getMenus().get(2); //TODO: third - Feed button/label, implement
        Label firstLabel = (Label)first.getGraphic();
        Label secondLabel = (Label)second.getGraphic();
        Label thirdLabel = (Label)third.getGraphic();

        if (firstLabel.getText().equals("Machines")) {
            BorderPane topPaneBefore = (BorderPane) panesToChange[0].getTop();
            Button topCenterBefore = (Button) topPaneBefore.getCenter();
            TextField topBottomBefore = (TextField) topPaneBefore.getBottom();
            TableView centerBefore = (TableView) panesToChange[0].getCenter();
            firstLabel.setOnMouseClicked(event -> {
                BorderPane topPane = (BorderPane) panesToChange[0].getTop();
                topPane.setCenter(topCenterBefore);
                topPane.setBottom(topBottomBefore);
                panesToChange[0].setCenter(centerBefore);
            });
            ((Menu) second).getItems().get(0).setOnAction(event -> {
                BorderPane topPane = (BorderPane) panesToChange[0].getTop();
                topPane.setCenter(CreateModules.createSettingsPane());
                topPane.setBottom(null);
                panesToChange[0].setCenter(null);
            });
            ((Menu) second).getItems().get(1).setOnAction(event -> {
                BorderPane topPane = (BorderPane) panesToChange[0].getTop();
                topPane.setCenter(CreateModules.createImportPane()); //TODO here will be importGridPane
                topPane.setBottom(null);
                panesToChange[0].setCenter(null);
            });
//            thirdLabel.setOnMouseClicked(event -> {
//                panesToChange[1].setCenter(null);
//                panesToChange[0].setCenter(pgCashOutHistoryTable);
//                refreshCashOutTable(station);
//            });
        }
    }

    static void refreshCashOutTable(PgStation station) {
        try {
            CreateModules.pgCashOutHistories = DatabaseReader.readDataFromPgCashOutHistory("SELECT * FROM pg_cash_out_history where IDstation = " + station.getIdStation() + ";");
            CreateModules.pgCashOutHistoryTable.setItems(CreateModules.pgCashOutHistories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void refreshStationTable() {
        try {
            CreateModules.pgStations = DatabaseReader.readDataFromPgStation("SELECT sm.IDstation, cd.IDcashDesk, st.name, st.deleted, sm.current_in, sm.current_out, sm.current_bet, sm.current_win, sm.current_game, st.date_last_in, b.name FROM pg_settlement_machine sm JOIN (SELECT IDrecord, MAX(IDrecord) AS lastone FROM pg_settlement_machine GROUP BY IDstation) sm2 ON sm.IDrecord = sm2.IDrecord INNER JOIN pg_station st ON sm.IDstation = st.IDstation INNER JOIN pg_cash_desk cd ON st.IDcashDesk = cd.IDcashDesk INNER JOIN pg_bar b ON cd.IDbar = b.IDbar GROUP BY sm.IDstation");
            CreateModules.pgStationTable.setItems(CreateModules.pgStations);
            includeProperties();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void refreshSettlementMachineTable(PgStation station, BigDecimal amount) {
        try {
            CreateModules.pgSettlementMachines = DatabaseReader.readDataFromPgSettlementMachine("SELECT * FROM pg_settlement_machine where IDstation = " + station.getIdStation() + ";");
            CreateModules.pgSettlementMachineTable.setItems(CreateModules.pgSettlementMachines);
            station.setAvailableBalance(station.getAvailableBalance().subtract(amount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Properties readProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/config.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    static void saveProperties(String... stringProp) {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("src/config.properties");

            prop.setProperty("default_sort_column", stringProp[0]);
            prop.setProperty("default_sort_desc", stringProp[1]);
            prop.setProperty("cache_server", stringProp[2]);
            prop.setProperty("cache_port", stringProp[3]);

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    static void includeProperties() {
        TableColumn col = getTableColumnByName(CreateModules.pgStationTable, readProperties().getProperty("default_sort_column"));
        String sortType = readProperties().getProperty("default_sort_desc");
        try {
            if (sortType.equals("0")) {
                col.setSortType(TableColumn.SortType.DESCENDING);
            } else if (sortType.equals("1")) {
                col.setSortType(TableColumn.SortType.ASCENDING);
            }
            CreateModules.pgStationTable.getSortOrder().add(col);
        } catch (NullPointerException e) {
            System.out.println("Incorrect table column name, check includeProperties method in ModulesController");
        }
    }
        //TODO: understand
    private static <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }
}