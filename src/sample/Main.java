package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class Main extends Application {

    static UserInterface ui;
    static Controller listManagerController;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        ui = new UserInterface(1200,900);
        listManagerController = new Controller(ui);
        Scene scene = new Scene(ui, ui.getWinWidth(), ui.getWinHeight());

        //Scene scene
        primaryStage.setTitle("List Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}



