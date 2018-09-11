package gui;

import db.readers.DatabaseReader;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import structures.PgStation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static java.time.LocalDateTime.now;

class StationTable extends TableView<PgStation> {

    StationTable() {
        setStationTableRowFactory(this);
        defineColumns();
        repopulate();
        includeProperties();
    }

    void repopulate() {
        try {
            setItems(DatabaseReader.readDataFromPgStation("SELECT sm.IDstation, cd.IDcashDesk, st.name, st.deleted, sm.current_in, sm.current_out, sm.current_bet, sm.current_win, sm.current_game, st.date_last_in, b.name FROM pg_settlement_machine sm JOIN (SELECT IDrecord, MAX(IDrecord) AS lastone FROM pg_settlement_machine GROUP BY IDstation) sm2 ON sm.IDrecord = sm2.IDrecord INNER JOIN pg_station st ON sm.IDstation = st.IDstation INNER JOIN pg_cash_desk cd ON st.IDcashDesk = cd.IDcashDesk INNER JOIN pg_bar b ON cd.IDbar = b.IDbar GROUP BY sm.IDstation"));
            includeProperties();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void defineColumns() {
        TableColumn<PgStation, Integer> idStation = new TableColumn<>("IDstation");
        idStation.setCellValueFactory(new PropertyValueFactory<>("idStation"));

        TableColumn<PgStation, String> barName = new TableColumn<>("Bar");
        barName.setCellValueFactory(new PropertyValueFactory<>("barName"));

        TableColumn<PgStation, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<PgStation, Integer> deleted = new TableColumn<>("Actual State");
        deleted.setCellValueFactory(new PropertyValueFactory<>("deleted"));

        TableColumn<PgStation, BigDecimal> inMultiplier = new TableColumn<>("In");
        inMultiplier.setCellValueFactory(new PropertyValueFactory<>("inMultiplier"));

        TableColumn<PgStation, BigDecimal> outMultiplier = new TableColumn<>("Out");
        outMultiplier.setCellValueFactory(new PropertyValueFactory<>("outMultiplier"));

        TableColumn<PgStation, BigDecimal> betMultiplier = new TableColumn<>("Bet");
        betMultiplier.setCellValueFactory(new PropertyValueFactory<>("betMultiplier"));

        TableColumn<PgStation, BigDecimal> winMultiplier = new TableColumn<>("Win");
        winMultiplier.setCellValueFactory(new PropertyValueFactory<>("winMultiplier"));

        TableColumn<PgStation, BigDecimal> gameMultiplier = new TableColumn<>("Game");
        gameMultiplier.setCellValueFactory(new PropertyValueFactory<>("gameMultiplier"));

        TableColumn<PgStation, LocalDateTime> dateLastIn = new TableColumn<>("Date last in");
        dateLastIn.setCellValueFactory(new PropertyValueFactory<>("dateLastIn"));

        TableColumn<PgStation, BigDecimal> availableBalance = new TableColumn<>("Available balance");
        availableBalance.setCellValueFactory(new PropertyValueFactory<>("availableBalance"));

        getColumns().addAll(Arrays.asList(idStation, barName, name, dateLastIn, deleted, inMultiplier, outMultiplier, betMultiplier, winMultiplier, gameMultiplier, availableBalance));
    }

    private void setStationTableRowFactory(StationTable stationTable) {
        setRowFactory(row -> new TableRow<>() {
            @Override
            public void updateItem(PgStation item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setStyle("");
            } else {
                for (int i = 0; i < getChildren().size(); i++) {
                    getChildren().get(i).setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                            CreateModules.createSecondWindow(stationTable, item);
                        }
//                            if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
//                                CreateModules.pgStationTable.getColumns().get(i);
//                            }
                    });
                    if (item.getDeleted() == 1) {
                        ((Labeled) getChildren().get(i)).setTextFill(Color.GREY);
                        ((Labeled) getChildren().get(i)).setTooltip(null);
                        //TODO the next line doesn't work:
                        getChildren().get(i).setStyle("-fx-strikethrough: true");
                    } else {
                        if ((item.getDateLastInAsLocalDateTime() != null) && getYesterday().isAfter(item.getDateLastInAsLocalDateTime())) {
                            ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                            ((Labeled) getChildren().get(i)).setTooltip(createTooltip(calculateDateToShowInTooltip(item.getDateLastInAsLocalDateTime())));
                        } else {
                            ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
                            ((Labeled) getChildren().get(i)).setTooltip(null);
                        }
                    }
                }
            }
            }
        });
    }

    private Tooltip createTooltip(String value) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(value);
        return tooltip;
    }

    private String calculateDateToShowInTooltip(LocalDateTime dateToSubtract) {
        LocalDateTime toDateTime = now();

        LocalDateTime tempDateTime = LocalDateTime.from( dateToSubtract );

        long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );

        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);

        return "Station is inactive " + years + " years, " + months + " months, " + days + " days, " + hours + " hours";
    }

//    private String calculateDateToShowInTooltip(Timestamp date1) {
//        Timestamp date2 = new Timestamp(System.currentTimeMillis());
//        Calendar a = getCalendar(date1);
//        Calendar b = getCalendar(date2);
//        int diffInYears = b.get(YEAR) - a.get(YEAR);
//        int diffInMonths = b.get(MONTH) - a.get(MONTH);
//        int diffInDays = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);
//        int diffInHours = b.get(Calendar.HOUR_OF_DAY) - a.get(Calendar.HOUR_OF_DAY);
//
//        return timeController(diffInYears,diffInMonths, diffInDays, diffInHours);
//    }
//
//    private String timeController(int year, int month, int day, int hour) {
//        if (hour < 0) {
//            hour += 24;
//            day -= 1;
//        } if (day <= 0) {
//            day += 30;
//            month -= 1;
//        } if (month <= 0) {
//            month += 12;
//            year -= 1;
//        }
//        return "Station is inactive " + year + " years, " + month + " months, " + day + " days, " + hour + " hours";
//    }

//    private Calendar getCalendar(Date date) {
//        Calendar cal = Calendar.getInstance(Locale.US);
//        cal.setTime(date);
//        return cal;
//    }

    private TableColumn<PgStation, ?> getTableColumnByName(String name) {
        for (TableColumn<PgStation, ?> col : getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }

    private LocalDateTime getYesterday() {
        return now().minusDays(1);
    }

    void includeProperties() {
        TableColumn<PgStation, ?> col = getTableColumnByName(ModulesController.readProperties().getProperty("default_sort_column"));
        String sortType = ModulesController.readProperties().getProperty("default_sort_desc");
        try {
            if (sortType.equals("0")) {
                col.setSortType(TableColumn.SortType.DESCENDING);
            } else if (sortType.equals("1")) {
                col.setSortType(TableColumn.SortType.ASCENDING);
            }
            getSortOrder().add(col);
        } catch (NullPointerException e) {
            System.out.println("Incorrect table column name: " + ModulesController.readProperties().getProperty("default_sort_column") + " in Station Table 127");
        }
    }

    SortedList<PgStation> tableFilter(TextField filterField) {
        FilteredList<PgStation> filteredData = new FilteredList<>(getItems(), predicate -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(pgStation -> {
            String lowerCaseFilter = newValue.toLowerCase();
            if (newValue.isEmpty()) { return true; }
            if ((pgStation.getDateLastInAsLocalDateTime() != null) && (newValue.length() > 2) && ((newValue.charAt(0) == '>') || (newValue.charAt(0) == '<'))) {
                String possibleDate = newValue.substring(1);
                if (newValue.charAt(1) != '=') {
                    try {
                        if ((newValue.charAt(0) == '>') && pgStation.getDateLastInAsLocalDateTime().isAfter(LocalDateTime.parse(possibleDate))) {
                            return true;
                        } else if ((newValue.charAt(0) == '<') && LocalDateTime.parse(possibleDate).isAfter(pgStation.getDateLastInAsLocalDateTime())) {
                            return true;
                        }
                    } catch (IllegalArgumentException e1) {
                    }
                }
                if ((newValue.charAt(1) == '=')) {
                    String secondPossibleDate = newValue.substring(2);
                    try { //<=2017-12-19 03:30:20
                        if ((newValue.charAt(0) == '>') && (pgStation.getDateLastInAsLocalDateTime()).isAfter(LocalDateTime.parse(possibleDate)) || pgStation.getDateLastIn().equals(possibleDate)) {
                            return true;
                        } else if ((newValue.charAt(0) == '<') && (pgStation.getDateLastInAsLocalDateTime().isAfter(LocalDateTime.parse(possibleDate)) || pgStation.getDateLastIn().equals(possibleDate))) {
                            return true;
                        }
                    } catch (IllegalArgumentException e1) {
                    }
                }
            } else if ((pgStation.getName() != null) && pgStation.getName().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if (pgStation.getBarName().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if (pgStation.getIdStation().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if ((pgStation.getInMultiplier() != null) && pgStation.getInMultiplier().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else if ((pgStation.getOutMultiplier() != null) && pgStation.getOutMultiplier().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
            else return (pgStation.getAvailableBalance() != null) && pgStation.getAvailableBalance().toString().toLowerCase().contains(lowerCaseFilter);
            return false;
        }));
        return new SortedList<>(filteredData);
    }
}
