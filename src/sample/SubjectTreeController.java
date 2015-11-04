package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeController implements EventHandler<ActionEvent>{

    private SubjectTreeModel model;
    private SubjectTreeView view;

    @Override
    public void handle(ActionEvent e)
    {
        if(e.getTarget().getClass().toString().equals("class javafx.scene.control.MenuItem")){

            if(((MenuItem)e.getTarget()).getId().equals("add_menu_item"))
                System.out.println("Adding");
            else if(((MenuItem) e.getTarget()).getId().equals("delete_menu_item")){
                System.out.println("Deleteing");
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
