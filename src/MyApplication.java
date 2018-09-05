
import gui.CreateModules;
import gui.ModulesController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("My application");
//        primaryStage:scene:primaryLayout:borderPane:
//                                                    top:
//                                                        topPane:
//                                                                top:menuBar
//                                                                center:filterButton
//                                                                bottom:filterField
//                                                    center:PgStationTable
        StackPane primaryLayout = new StackPane();
        primaryLayout.autosize();

        BorderPane topPane = new BorderPane();
        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = CreateModules.createMenuBar("Settings","Machines", "Settings", "Feed");
        topPane.setTop(menuBar);

        TextField filterField = new TextField();
        filterField.setVisible(false);
        topPane.setBottom(filterField);
        topPane.setCenter(CreateModules.createFilterButton(topPane));

        borderPane.setCenter(CreateModules.getPgStationTable());
        borderPane.setTop(topPane);
        ModulesController.setOnActionFirstMenuButtons(menuBar, borderPane);

        primaryLayout.getChildren().addAll(borderPane);

        Scene scene = new Scene(primaryLayout);

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add("src/labels-with-strikethrough.css");
    }
}
