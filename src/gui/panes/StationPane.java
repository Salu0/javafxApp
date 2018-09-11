package gui;

import gui.tables.StationTable;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import structures.PgStation;

public class StationPane extends StackPane {

    public StationPane() {
        autosize();
        BorderPane topPane = new BorderPane();
        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = createMenuBar();
        topPane.setTop(menuBar);

        TextField filterField = new TextField();
        filterField.setVisible(false);
        topPane.setBottom(filterField);
        StationTable stationTable = new StationTable();
        topPane.setCenter(createFilterButton(topPane, stationTable));

        borderPane.setCenter(stationTable);
        borderPane.setTop(topPane);
        ModulesController.setOnActionFirstMenuButtons(menuBar, borderPane);

        getChildren().addAll(borderPane);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu machines = new Menu();
        machines.setGraphic(new Label("Machines"));
        Menu feed = new Menu();
        feed.setGraphic(new Label("Feed"));

        Menu settings = new Menu();
        settings.setGraphic(new Label("Settings"));
        MenuItem settingsVertical = new MenuItem("Settings");
        MenuItem importNewData = new MenuItem("Import new data");
        settings.getItems().addAll(settingsVertical, importNewData);

        menuBar.getMenus().addAll(machines, settings, feed);
        return menuBar;
    }

    private Button createFilterButton(BorderPane top, StationTable stationTable) {
        Button button = new Button("Show filters");

        button.setOnAction(e -> {
            if (button.getText().equals("Show filters")) {
                button.setText("Hide filters");
                TextField filterField = new TextField();
                filterField.setVisible(true);
                top.setBottom(filterField);

                SortedList<PgStation> sortedData = stationTable.tableFilter(filterField);
                sortedData.comparatorProperty().bind(stationTable.comparatorProperty());
                stationTable.setItems(sortedData);
            }
            else {
                button.setText("Show filters");
                top.getBottom().setVisible(false);
            }
        });
        return button;
    }
}
