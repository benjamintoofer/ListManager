package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Main extends Application {

    static RunListManager RLM;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
       //Image image = new Image("/Users/benjamintoofer/Documents/DU/Fall2015/COMP3381/Project_ListManager/ListManager/LMICON.png");
        //primaryStage.getIcons().add(image);
        RLM = new RunListManager(primaryStage);
        RLM.getIoController().addObserver(RLM);
        Scene scene = new Scene(RLM.getUIView(), RLM.getUIView().getWinWidth(), RLM.getUIView().getWinHeight());

        //Scene scene
        primaryStage.setTitle("List Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        Application.launch(args);
    }

}



