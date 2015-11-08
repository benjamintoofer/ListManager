package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeController implements EventHandler<ActionEvent>{

    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;
    private SubjectTreeView subjectTreeView;

    private DialogBox addClassDialogBox = new DialogBox("Class",true);
    private DialogBox removeClassDialogBox = new DialogBox("Class",false);

    @Override
    public void handle(ActionEvent e)
    {
        if(e.getTarget().getClass().toString().equals("class javafx.scene.control.MenuItem")){

            if(((MenuItem)e.getTarget()).getId().equals("add_menu_item")){


                addClassDialogBox.setClassTextField("");
                addClassDialogBox.setDescTextField("");

                Optional<ButtonType> result = addClassDialogBox.showAndWait();
                if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                    if(subjectTreeView.getSelectedTreeItem().isAssociated()){
                        associationModel.removeAssociation(subjectTreeView.getSelectedTreeItem());
                    }

                    Subject addSubject = new Subject(addClassDialogBox.getClassName(),addClassDialogBox.getDesc());
                    subjectTreeModel.setSubjectAssociated(subjectTreeView.getSelectedTreeItem(),false);
                    subjectTreeModel.addItem(subjectTreeView.getSelectedTreeItem(),addSubject);
                }

                System.out.println("Printing associations after add:\n" + associationModel.printAssociations());

            }else if(((MenuItem) e.getTarget()).getId().equals("delete_menu_item")){

                Optional<ButtonType> result = removeClassDialogBox.showAndWait();
                if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                    Subject subjectToRemove = subjectTreeView.getSelectedTreeItem();
                    if(subjectTreeView.getSelectedTreeItem().isLeaf()){

                        associationModel.removeAssociation(subjectToRemove);


                    }else{

                        ArrayList<Subject> childrenList = subjectTreeModel.getChildrenFromNode(subjectToRemove);
                        for(Subject s: childrenList){
                            subjectTreeModel.setSubjectAssociated(s,false);
                            associationModel.removeAssociation(s);
                        }

                    }

                    subjectTreeModel.removeItem(subjectToRemove);

                }

            }
        }
    }

    public void addSubjectTreeModel(SubjectTreeModel model){

        this.subjectTreeModel = model;
    }

    public void addAssociationModel(AssociationModel model){

        this.associationModel = model;
    }

    public void addSubjectTreeView(SubjectTreeView view){

        this.subjectTreeView = view;
    }

}
