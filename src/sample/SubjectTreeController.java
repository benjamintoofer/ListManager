package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeController implements EventHandler<ActionEvent>, Serializable{

    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;
    private SubjectTreeView subjectTreeView;

    private transient DialogBox addClassDialogBox = new DialogBox("Subject",true);
    private transient DialogBox removeClassDialogBox = new DialogBox("Subject",false);

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

                    UserInterface.getTextArea().setText("Added Subject: "+addClassDialogBox.getClassName() + " to Subject: "+subjectTreeView.getSelectedTreeItem().getName());
                }

                System.out.println("Printing associations after add:\n" + associationModel.printAssociations());

            }else if(((MenuItem) e.getTarget()).getId().equals("delete_menu_item")){

                removeClassDialogBox.setHeaderText(subjectTreeView.getSelectedTreeItem().getName());
                Optional<ButtonType> result = removeClassDialogBox.showAndWait();

                if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                    Subject subjectToRemove = subjectTreeView.getSelectedTreeItem();
                    if(subjectTreeView.getSelectedTreeItem().isLeaf()){

                        for(Association a : associationModel.queryBySubject(subjectToRemove)){
                            subjectTreeModel.setSubjectAssociated(subjectToRemove,false);
                        }
                        subjectTreeModel.updateTreeColorAssociation(subjectToRemove);
                        associationModel.removeAssociation(subjectToRemove);


                    }else{

                        ArrayList<Subject> childrenList = subjectTreeModel.getChildrenFromNodePostOrder(subjectToRemove);
                        for(Subject s: childrenList){
                            for(Association a : associationModel.queryBySubject(subjectToRemove)){
                                subjectTreeModel.setSubjectAssociated(subjectToRemove,false);
                            }
                            subjectTreeModel.updateTreeColorAssociation(s);
                            associationModel.removeAssociation(s);
                        }

                    }

                    subjectTreeModel.removeItem(subjectToRemove);

                    UserInterface.getTextArea().setText("Deleted Subject: "+subjectToRemove);
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
