package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// 100k row table: group by:
// OK: simple GroupBy
// OK: GroupBy, with 1 and 2 functions as projection
// OK: GroupBy, 1 funcSelect, 1 HavingFunc
// OK: GroupBy, 4 FuncSelect, 1 HavingFunc

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mango Bango");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
