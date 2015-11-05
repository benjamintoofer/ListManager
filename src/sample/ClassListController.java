package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

import java.util.Optional;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class ClassListController implements EventHandler<ActionEvent> {

    private ClassListModel classListModel;
    private ClassListView classListView;
    private DialogBox addClassDialogBox = new DialogBox(true);
    private DialogBox removeClassDialogBox = new DialogBox(false);

    @Override
    public void handle(ActionEvent e)
    {

        if(e.getTarget().getClass().toString().equals("class javafx.scene.control.Button")){

            if(((Button)e.getTarget()).getId().equals("add_course_button")) {

                addClassDialogBox.setClassTextField("");
                addClassDialogBox.setDescTextField("");

                Optional<ButtonType> result = addClassDialogBox.showAndWait();
                if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                    classListModel.addClassToList(new Class(addClassDialogBox.getClassName(),addClassDialogBox.getDesc()));
                }


            }else if(((Button) e.getTarget()).getId().equals("remove_course_button")){

                if(classListView.getSelectedClass() != null){

                    removeClassDialogBox.setDialogContentText(classListView.getSelectedClass());
                    Optional<ButtonType> result = removeClassDialogBox.showAndWait();

                    if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){
                        classListModel.removeClassFromList(classListView.getSelectedClass());
                    }
                }else{
                    System.out.println("Nothing selected");

                }


            }
        }
    }

    public void addView(ClassListView view){

        classListView = view;
    }

    public void addModel(ClassListModel model){

        classListModel = model;
    }
}
