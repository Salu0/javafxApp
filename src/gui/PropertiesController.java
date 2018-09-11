package gui;

import gui.tables.CashOutHistoryTable;
import gui.tables.StationTable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import structures.PgStation;

import java.io.*;
import java.util.Properties;

public class ModulesController {

    public static void setOnActionSecondMenuButtons(MenuBar menuBar, CashOutHistoryTable cashOutHistoryTable, PgStation station, BorderPane... panesToChange) {
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

                cashOutHistoryTable.repopulate();
                panesToChange[0].setCenter(cashOutHistoryTable);
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
                topPane.setCenter(CreateModules.createSettingsPane((StationTable) panesToChange[0].getCenter()));
                topPane.setBottom(null);
                panesToChange[0].setCenter(null);
            });
            ((Menu) second).getItems().get(1).setOnAction(event -> {
                BorderPane topPane = (BorderPane) panesToChange[0].getTop();
                topPane.setCenter(CreateModules.createImportPane((StationTable) panesToChange[0].getCenter()));
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

    public static Properties readProperties() {
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

    public static void saveProperties(String... stringProp) {
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
}