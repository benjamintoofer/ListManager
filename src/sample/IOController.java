package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by benjamintoofer on 11/8/15.
 */
public class IOController implements EventHandler<ActionEvent>{

    public IOController(){

    }

    @Override
    public void handle(ActionEvent event)
    {
        System.out.println("IO HANDLING!!!!");
    }
}
