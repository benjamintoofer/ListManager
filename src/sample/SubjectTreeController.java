package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

import java.util.Optional;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeController implements EventHandler<ActionEvent>{

    private SubjectTreeModel model;
    private SubjectTreeView view;

    private DialogBox addClassDialogBox = new DialogBox(true);
    private DialogBox removeClassDialogBox = new DialogBox(false);

    @Override
    public void handle(ActionEvent e)
    {
        if(e.getTarget().getClass().toString().equals("class javafx.scene.control.MenuItem")){

            if(((MenuItem)e.getTarget()).getId().equals("add_menu_item")){


                addClassDialogBox.setClassTextField("");
                addClassDialogBox.setDescTextField("");

                Optional<ButtonType> result = addClassDialogBox.showAndWait();
                if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                    Subject addSubject = new Subject(addClassDialogBox.getClassName(),addClassDialogBox.getDesc());
                    model.addItem(view.getSelectedTreeItem(),addSubject);
                }

            }else if(((MenuItem) e.getTarget()).getId().equals("delete_menu_item")){

                Optional<ButtonType> result = removeClassDialogBox.showAndWait();
                if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                    Subject subjectToRemove = view.getSelectedTreeItem();
                    model.removeItem(subjectToRemove);
                }

            }
        }
    }

    public void addModel(SubjectTreeModel model){
        this.model = model;
    }

    public void addView(SubjectTreeView view){
        this.view = view;
    }

}
