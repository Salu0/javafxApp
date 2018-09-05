package gui;

import db.readers.DatabaseReader;
import db.writers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import structures.PgCashOutHistory;
import structures.PgSettlementMachine;
import structures.PgStation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import static gui.ModulesController.refreshSettlementMachineTable;
import static gui.ModulesController.refreshStationTable;


public class CreateModules {

    private static Stage secondWindow = new Stage();
    static ObservableList<PgStation> pgStations = FXCollections.observableArrayList();
    static TableView<PgStation> pgStationTable = new TableView<>();
    static ObservableList<PgSettlementMachine> pgSettlementMachines = FXCollections.observableArrayList();
    static TableView<PgSettlementMachine> pgSettlementMachineTable = new TableView<>();
    static ObservableList<PgCashOutHistory> pgCashOutHistories = FXCollections.observableArrayList();
    static TableView<PgCashOutHistory> pgCashOutHistoryTable = new TableView<>();

    public static MenuBar createMenuBar(String nameOfMenuItemWithSubMenu, String... menuItems) {
        MenuBar menuBar = new MenuBar();
        for (String nameOfMenuItem : menuItems) {
            if ((nameOfMenuItemWithSubMenu != null) && (nameOfMenuItemWithSubMenu.equals(nameOfMenuItem))) {
                Label labelWithSubMenu = new Label(nameOfMenuItemWithSubMenu);
                Menu itemWithSubMenu = new Menu();
                MenuItem settings = new MenuItem("Settings");
                MenuItem importNewData = new MenuItem("Import new data");

                itemWithSubMenu.getItems().addAll(settings, importNewData);

                itemWithSubMenu.setGraphic(labelWithSubMenu);
                menuBar.getMenus().add(itemWithSubMenu);
            } else {
                Label label = new Label(nameOfMenuItem);
                Menu item = new Menu();
                item.setGraphic(label);
                menuBar.getMenus().add(item);
            }
        }
        return menuBar;
    }

    public static TableView getPgStationTable() throws SQLException {
        createCashOutHistoryTableView();
        pgStations = DatabaseReader.readDataFromPgStation("SELECT sm.IDstation, cd.IDcashDesk, st.name, st.deleted, sm.current_in, sm.current_out, sm.current_bet, sm.current_win, sm.current_game, st.date_last_in, b.name FROM pg_settlement_machine sm JOIN (SELECT IDrecord, MAX(IDrecord) AS lastone FROM pg_settlement_machine GROUP BY IDstation) sm2 ON sm.IDrecord = sm2.IDrecord INNER JOIN pg_station st ON sm.IDstation = st.IDstation INNER JOIN pg_cash_desk cd ON st.IDcashDesk = cd.IDcashDesk INNER JOIN pg_bar b ON cd.IDbar = b.IDbar GROUP BY sm.IDstation");

        pgStationTable.setRowFactory(row -> new TableRow<>() {

            @Override
            public void updateItem(PgStation item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    for(int i=0; i < getChildren().size(); i++) {
                        getChildren().get(i).setOnMouseClicked(event -> {
                            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                                createSecondWindow(item);
                            }
                        });
                        if (item.getDeleted() == 1) {
                            ((Labeled) getChildren().get(i)).setTextFill(Color.GREY);
                            ((Labeled) getChildren().get(i)).setTooltip(null);
                            //TODO the next line doesn't work:
                            getChildren().get(i).setStyle("-fx-strikethrough: true");
                        } else {
                            if ((item.getDateLastInAsTimestamp() != null) && ModulesController.getDateSinceInactive().after(item.getDateLastInAsTimestamp())) {
                                ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                                ((Labeled) getChildren().get(i)).setTooltip(CreateModules.createTooltip(ModulesController.calculateDateToShowInTooltip(item.getDateLastInAsTimestamp())));
                            } else {
                                ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
                                ((Labeled) getChildren().get(i)).setTooltip(null);
                            }
                        }
                    }
                }
            }
        });

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

        TableColumn<PgStation, Timestamp> dateLastIn = new TableColumn<>("Date last in");
        dateLastIn.setCellValueFactory(new PropertyValueFactory<>("dateLastIn"));

        TableColumn<PgStation, BigDecimal> availableBalance = new TableColumn<>("Available balance");
        availableBalance.setCellValueFactory(new PropertyValueFactory<>("availableBalance"));

        pgStationTable.setItems(pgStations);
        pgStationTable.getColumns().addAll(idStation, barName, name, dateLastIn, deleted, inMultiplier, outMultiplier, betMultiplier, winMultiplier, gameMultiplier, availableBalance);
        ModulesController.includeProperties();
        return pgStationTable;
    }

    private static Tooltip createTooltip(String value) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(value);
        return tooltip;
    }

    private static TableView createFilledSettlementMachineTable(Integer id) {

        String selectFromPgSettlementMachine = "SELECT * FROM pg_settlement_machine where IDstation = " + id + ";";
        try {
            pgSettlementMachines = DatabaseReader.readDataFromPgSettlementMachine(selectFromPgSettlementMachine);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        pgSettlementMachineTable.setItems(pgSettlementMachines);
        pgSettlementMachineTable.getColumns().addAll(idRecord, idPrevRecord, idSettlement, idStation, date, currentIn, currentOut, currentBet, currentWin, currentGame, currentTicketIn, currentTicketOut, currentPromoIn, currentPromoOut, currentJackpotOut, oldIn, oldOut, newIn, newOut, totalIn, totalOut, totalTicketIn, totalTicketOut, totalPromoIn, totalPromoOut, totalJackpotOut, totalBet, totalWin, totalGame, payoff, missingPayoff, manualEntry, manualInserted, idUser);
        return pgSettlementMachineTable;
    }

    private static void createSecondWindow(PgStation pgStationData) {
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.autosize();
        fillCashOutHistoryTableView(pgStationData.getIdStation());

        Scene secondScene = new Scene(secondaryLayout);
        secondScene.getStylesheets().add("src/labels-with-strikethrough.css");

            BorderPane primaryPane = new BorderPane();
            primaryPane.setCenter(createFilledSettlementMachineTable(pgStationData.getIdStation()));

                BorderPane hubPane = new BorderPane();

                    TextField amount = new TextField();
                    amount.setVisible(false);
                    Label validationFailed = new Label();
                    validationFailed.setVisible(false);

                    if ((pgStationData.getAvailableBalance() != null) && (pgStationData.getAvailableBalance().intValue() > 0)) {
                        BorderPane cashOutPane = new BorderPane();
                        cashOutPane.setTop(createCashOutMoneyButton(pgStationData, amount, validationFailed));
                        cashOutPane.setAlignment(cashOutPane.getTop(), Pos.TOP_CENTER);
                        cashOutPane.setCenter(amount);
                        cashOutPane.setBottom(validationFailed);
                        hubPane.setCenter(cashOutPane);
                    }

                    MenuBar bar = createMenuBar(null,"Statistic", "Cash out history");

                hubPane.setTop(bar);


            primaryPane.setTop(hubPane);
            ModulesController.setOnActionSecondMenuButtons(bar, pgStationData, primaryPane, hubPane);

        secondaryLayout.getChildren().add(primaryPane);
        secondWindow.setScene(secondScene);
        secondWindow.show();
    }

    private static void fillCashOutHistoryTableView(Integer stationId) {
        try {
            pgCashOutHistories = DatabaseReader.readDataFromPgCashOutHistory("SELECT * FROM pg_cash_out_history where IDstation = " + stationId + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCashOutHistoryTableView() {
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

        pgCashOutHistoryTable.setItems(pgCashOutHistories);
        pgCashOutHistoryTable.getColumns().addAll(id, idStation, dateOfRecord, cashOutAmount, comment);
    }

    static GridPane createSettingsPane() {
        Properties prop = ModulesController.readProperties();
        GridPane settingsInfo = new GridPane();

        settingsInfo.add(new Label("Default sort column:    "), 0, 0);
        TextField defaultSortColumn = new TextField(prop.getProperty("default_sort_column"));
        createColumnOptions(defaultSortColumn, "IDstation", "Bar", "Name", "Date last in", "Actual State", "In", "Out", "Bet", "Win", "Game", "Available balance");
        settingsInfo.add(defaultSortColumn, 1, 0);

        settingsInfo.add(new Label("Default sort desc:"), 0, 1);
        TextField defaultSortDesc = new TextField(prop.getProperty("default_sort_desc"));
        createColumnOptions(defaultSortDesc, "0 (Descending order)", "1 (Ascending order)");
        settingsInfo.add(defaultSortDesc, 1, 1);

        settingsInfo.add(new Label("Cache server:"), 0, 2);
        TextField cacheServer = new TextField(prop.getProperty("cache_server"));
        settingsInfo.add(cacheServer, 1, 2);

        settingsInfo.add(new Label("Cache port:"), 0, 3);
        TextField cachePort = new TextField(prop.getProperty("cache_port"));
        settingsInfo.add(cachePort, 1, 3);

        settingsInfo.add(createSaveButtonForSettingsPane(defaultSortColumn, defaultSortDesc, cacheServer, cachePort), 1, 4);
        return settingsInfo;
    }

    static GridPane createImportPane() {
        GridPane importPane = new GridPane();

        importPane.add(new Label("Select path to import pg_station table:"), 0, 0);
        TextField pgStationPath = new TextField();
        importPane.add(pgStationPath, 1, 0);
        importPane.add(createBrowseButton(pgStationPath), 2, 0);

        importPane.add(new Label("Select path to import pg_settlement_machine table:    "), 0, 1);
        TextField pgSettlementMachinePath = new TextField();
        importPane.add(pgSettlementMachinePath, 1, 1);
        importPane.add(createBrowseButton(pgSettlementMachinePath), 2, 1);

        importPane.add(new Label("Select path to import pg_bar table:"), 0, 2);
        TextField pgBarPath = new TextField();
        importPane.add(pgBarPath, 1, 2);
        importPane.add(createBrowseButton(pgBarPath), 2, 2);

        importPane.add(new Label("Select path to import pg_cash_desk table:"), 0, 3);
        TextField pgCashDeskTable = new TextField();
        importPane.add(pgCashDeskTable, 1, 3);
        importPane.add(createBrowseButton(pgCashDeskTable), 2, 3);

        importPane.add(createImportButtonForImportPane(pgStationPath, pgSettlementMachinePath, pgBarPath, pgCashDeskTable), 1, 4);
        return importPane;
    }

    private static Button createBrowseButton(TextField textField) {
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            String path = "";
            try {
                path = fileChooser.showOpenDialog(new Stage()).toPath().toString();
            } catch (NullPointerException e) {
                System.out.println("User didn't choose file");
            }
            textField.setText(path);
        });
        return browseButton;
    }

    private static void createColumnOptions(TextField defaultSortColumnField, String... options) {
        ContextMenu contextMenu = new ContextMenu();

        for (String option : options) { contextMenu.getItems().add(new MenuItem(option)); }

        for (MenuItem menuItem : contextMenu.getItems()) {
            menuItem.setOnAction(event -> {
                defaultSortColumnField.setText(menuItem.getText());
                if (defaultSortColumnField.getText().startsWith("0") || defaultSortColumnField.getText().startsWith("1")) {
                    defaultSortColumnField.setText(Character.toString(defaultSortColumnField.getText().charAt(0)));
                }
            });
        }

        defaultSortColumnField.setOnMouseClicked(e -> {
            contextMenu.show(defaultSortColumnField, e.getScreenX(), e.getScreenY());
        });
    }

    private static Button createSaveButtonForSettingsPane(TextField... textFields) {
        Button save = new Button("Save");
        save.setOnAction(e -> {
            String[] properties = extractPropertyValuesFromTextFields(textFields);
            ModulesController.saveProperties(properties);
            refreshStationTable();
        });
        return save;
    }

    private static Button createImportButtonForImportPane(TextField... textFields) {
        Button importButton = new Button("Import");
        importButton.setOnAction(e -> {
            if (!textFields[0].getText().isEmpty()) {
                PgStationDBWriter.writeWithClearing(textFields[0].getText());
            } if (!textFields[1].getText().isEmpty()) {
//                TODO uncomment this after testing:
//                PgSettlementMachineDBWriter.writeWithClearing(textFields[1].getText());
            } if (!textFields[2].getText().isEmpty()) {
                PgBarDBWriter.writeWithClearing(textFields[2].getText());
            } if (!textFields[3].getText().isEmpty()) {
                PgCashDeskDBWriter.writeWithClearing(textFields[3].getText());
            }
            refreshStationTable();
        });
        return importButton;
    }

    private static String[] extractPropertyValuesFromTextFields(TextField... textFields) {
        String[] propertyValues = new String[4];
        int i = 0;
        for (TextField textField : textFields) {
            propertyValues[i] = textField.getText();
            i++;
        }
        return propertyValues;
    }

    private static void createAreYouSureWindow(PgStation station, String amount) {
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.autosize();
        Scene secondScene = new Scene(secondaryLayout);
        secondScene.getStylesheets().add("src/labels-with-strikethrough.css");
        BorderPane primaryPane = new BorderPane();
        Label rySure = new Label("Are you sure?");
        primaryPane.setTop(rySure);

        secondaryLayout.getChildren().add(primaryPane);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.WINDOW_MODAL);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);

        Button noButton = new Button("No");
        noButton.setPrefSize(100, 20);
        noButton.setOnAction(e -> newWindow.close());

        hbox.getChildren().addAll(createYesButton(station, newWindow, amount), noButton);

        primaryPane.setCenter(hbox);
        newWindow.initOwner(secondWindow);
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    private static Button createYesButton(PgStation station, Stage window, String amount) {
        Button yesButton = new Button("Yes");
        yesButton.setPrefSize(100, 20);
        yesButton.setOnAction(e -> {
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            BigDecimal decimalAmount = new BigDecimal(amount);
            PgCashOutHistory row = new PgCashOutHistory(station.getIdStation(), nowTime, decimalAmount, "Something");
            PgCashOutHistoryWriter.write(row);
            try {
                ObservableList<PgSettlementMachine> PgSettlementMachine = DatabaseReader.readDataFromPgSettlementMachine("SELECT * FROM pg_settlement_machine a JOIN (SELECT IDstation, MAX(IDrecord) IDrecord FROM pg_settlement_machine where IDstation = " + station.getIdStation() + " GROUP BY IDstation) b ON a.IDstation = b.IDstation AND a.IDrecord = b.IDrecord");
                PgSettlementMachine pgSettlementMachineToInsert = PgSettlementMachine.get(0);
                pgSettlementMachineToInsert.setIdPrevRecord(pgSettlementMachineToInsert.getIdRecord());
                pgSettlementMachineToInsert.setDate(nowTime);
                pgSettlementMachineToInsert.setTotalBet(new BigDecimal(0));
                pgSettlementMachineToInsert.setTotalWin(new BigDecimal(0));
                pgSettlementMachineToInsert.setTotalIn(new BigDecimal(0));

                pgSettlementMachineToInsert.setTotalOut(decimalAmount);
                BigDecimal result = decimalAmount.add(pgSettlementMachineToInsert.getCurrentOut());
                pgSettlementMachineToInsert.setCurrentOut(result);
                PgSettlementMachineDBWriter.write(pgSettlementMachineToInsert);
                refreshSettlementMachineTable(station,decimalAmount);
                ModulesController.refreshStationTable();
                if ((station.getAvailableBalance() == null) || (station.getAvailableBalance().intValue() == 0)) {
                    secondWindow.close();
                    createSecondWindow(station);
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            window.close();
        });
        return yesButton;
    }

    public static Button createFilterButton(BorderPane top) {
        Button button = new Button("Show filters");

        button.setOnAction(e -> {
            if (button.getText().equals("Show filters")) {
                button.setText("Hide filters");
                TextField filterField = new TextField();
                filterField.setVisible(true);
                top.setBottom(filterField);

                SortedList<PgStation> sortedData = ModulesController.tableFilter(filterField, pgStations);
                sortedData.comparatorProperty().bind(pgStationTable.comparatorProperty());
                pgStationTable.setItems(sortedData);
            }
            else {
                button.setText("Show filters");
                top.getBottom().setVisible(false);
            }
        });
        return button;
    }

    private static Button createCashOutMoneyButton(PgStation station, TextField amount, Label label) {
        Button cashOutButton = new Button("Cash out money");
        cashOutButton.setVisible(false);
            if (station.getAvailableBalance().intValue() > 0) {
                cashOutButton.setVisible(true);
                amount.setVisible(true);
                cashOutButton.setOnAction(event -> {
                    label.setVisible(false);
                    try {
                        if (new BigDecimal(amount.getText()).compareTo(station.getAvailableBalance()) == 1) {
                            label.setText("Amount must be less than: " + station.getAvailableBalance());
                            label.setTextFill(Color.RED);
                            label.setVisible(true);
                        } else {
                            createAreYouSureWindow(station, amount.getText());
                        }
                    } catch (NumberFormatException e) {
                        label.setText("Decimals only");
                        label.setTextFill(Color.RED);
                        label.setVisible(true);
                    }
                });
            }
        return cashOutButton;
    }
}