
import GUI.CreateModules;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("My application");

        Group root = new Group();

        BorderPane top = new BorderPane();
        BorderPane borderPane = new BorderPane();

        top.setTop(CreateModules.createMenuBar());
        top.setCenter(CreateModules.createButton(top));

        borderPane.setTop(top);

        borderPane.setBottom(CreateModules.createPg_stationTable());
        root.getChildren().addAll(borderPane);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add("src/labels-with-strikethrough.css");
    }
}
