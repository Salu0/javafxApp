package GUI;

import DBWriters.Readers.Pg_stationTableReader;
import Structures.Pg_station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public final class CreateModules {

    private static ObservableList<Pg_station> masterData = FXCollections.observableArrayList();

    private static TableView pg_stationTable = new TableView();

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CreateModules() {
        try {
            masterData = Pg_stationTableReader.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu machines = new Menu("Machines");
        Menu settings = new Menu("Settings");
        Menu liveFeed = new Menu("Feed");
        menuBar.getMenus().addAll(machines, settings, liveFeed);
        return menuBar;
    }

    public static TableView createPg_stationTable() throws SQLException {
        masterData = Pg_stationTableReader.read();

        TableColumn<Pg_station, Integer> idStation = new TableColumn<Pg_station,Integer>("IDstation");
        idStation.setCellValueFactory(new PropertyValueFactory<Pg_station,Integer>("idStation"));

        TableColumn<Pg_station, Integer> barName = new TableColumn<Pg_station,Integer>("Bar");
        barName.setCellValueFactory(new PropertyValueFactory<Pg_station,Integer>("barName"));

        TableColumn<Pg_station, Label> name = new TableColumn<Pg_station,Label>("Name");
        name.setCellValueFactory(new PropertyValueFactory<Pg_station,Label>("name"));

        TableColumn<Pg_station, Integer> deleted = new TableColumn<Pg_station,Integer>("Actual State");
        deleted.setCellValueFactory(new PropertyValueFactory<Pg_station,Integer>("deleted"));

        TableColumn<Pg_station, BigDecimal> inMultiplier = new TableColumn<Pg_station,BigDecimal>("In");
        inMultiplier.setCellValueFactory(new PropertyValueFactory<Pg_station,BigDecimal>("inMultiplier"));

        TableColumn<Pg_station, BigDecimal> outMultiplier = new TableColumn<Pg_station,BigDecimal>("Out");
        outMultiplier.setCellValueFactory(new PropertyValueFactory<Pg_station,BigDecimal>("outMultiplier"));

        TableColumn<Pg_station, BigDecimal> betMultiplier = new TableColumn<Pg_station,BigDecimal>("Bet");
        betMultiplier.setCellValueFactory(new PropertyValueFactory<Pg_station,BigDecimal>("betMultiplier"));

        TableColumn<Pg_station, BigDecimal> winMultiplier = new TableColumn<Pg_station,BigDecimal>("Win");
        winMultiplier.setCellValueFactory(new PropertyValueFactory<Pg_station,BigDecimal>("winMultiplier"));

        TableColumn<Pg_station, BigDecimal> gameMultiplier = new TableColumn<Pg_station,BigDecimal>("Game");
        gameMultiplier.setCellValueFactory(new PropertyValueFactory<Pg_station,BigDecimal>("gameMultiplier"));

        TableColumn<Pg_station, Timestamp> dateLastIn = new TableColumn<Pg_station,Timestamp>("Date last in");
        dateLastIn.setCellValueFactory(new PropertyValueFactory<Pg_station,Timestamp>("dateLastIn"));

        TableColumn<Pg_station, BigDecimal> availableBalance = new TableColumn<Pg_station,BigDecimal>("Available balance");
        availableBalance.setCellValueFactory(new PropertyValueFactory<Pg_station,BigDecimal>("availableBalance"));

        pg_stationTable.setItems(masterData);

        pg_stationTable.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseEvent -> System.out.println("mouse entered detected! " + mouseEvent.getSource()));

        pg_stationTable.setRowFactory(row -> new TableRow<Pg_station>(){
            @Override
            public void updateItem(Pg_station item, boolean empty){
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getDeleted() == 1) {
                        for(int i=0; i < getChildren().size(); i++) {
                            ((Labeled) getChildren().get(i)).setTextFill(Color.GREY);
                            //TODO those 2 next lines doesn't work:
                            getChildren().get(i).setStyle("-fx-strikethrough: true");
                            ((Labeled) getChildren().get(i)).setStyle("-fx-strikethrough: true");
                        }
                    } else {
                        if ((item.getDateLastInAsTimestamp() != null) && ModulesController.getDateSinceInactive().after(item.getDateLastInAsTimestamp())) {
                            for (int i=0; i < getChildren().size(); i++) {
                                ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                            }
                        }
                        else {
                            for(int i=0; i<getChildren().size(); i++) {
                                ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
                            }
                        }
                    }
                }
            }
        });

        pg_stationTable.getColumns().addAll(idStation, barName, name, dateLastIn, deleted, inMultiplier, outMultiplier, betMultiplier, winMultiplier, gameMultiplier, availableBalance);
        return pg_stationTable;
    }

    public static Button createButton(BorderPane top) {
        Button button = new Button("Show filters");
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if (button.getText().equals("Show filters")) {
                    button.setText("Hide filters");
                    TextField filterField = new TextField();
                    top.setBottom(filterField);
                    pg_stationTable.setItems(ModulesController.tableFilter(filterField, masterData));
                }
                else {
                    button.setText("Show filters");
                    top.setBottom(null);
                }
            }
        });
        return button;
    }
}

