package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


import java.util.Observable;
import java.util.Observer;

public class Controller implements EventHandler{

    private UserInterface ui;

    public Controller(UserInterface ui){

        this.ui = ui;


    }

    private void init(){

        ui.getClassListView().getAddCourseButton().setOnAction(new AddRemoveClassHandler());
        ui.getClassListView().getAddCourseButton().setOnAction(new AddRemoveClassHandler());
    }

    @Override
    public void handle(Event event)
    {

    }

    private class AddRemoveClassHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e){

            System.out.println("Handling");
            if(e.getTarget().getClass().toString().equals("class javafx.scene.control.Button")){

                if(((Button)e.getTarget()).getId().equals("add_course_button")){
                    System.out.println("Adding course");
                }
                if(((Button)e.getTarget()).getId().equals("remove_course_button")){
                    System.out.println("Removing course");

                    //ClassListView.removeCourse(ClassListView.getSelectedCourse());
                }
            }
        }

    }
}
