package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by benjamintoofer on 11/5/15.
 */

public class AssociationController implements EventHandler<ActionEvent>, Serializable{

    private AssociationModel associationModel;
    private SubjectTreeModel subjectTreeModel;

    private ClassListView classView;     //Using ClassListView because disconnect and connect buttons exist in ClassListView
    private SubjectTreeView subjectView;
    ArrayList<Subject> childrenList;

    @Override
    public void handle(ActionEvent e)
    {
        if(e.getTarget().getClass().toString().equals("class javafx.scene.control.Button")){

            if(((Button)e.getTarget()).getId().equals("connect_button")){

                if(classView.getSelectedClassByString() != null && subjectView.getSelectedTreeItem() != null) {

                    String className = classView.getSelectedClassByString();
                    Class selectedClass = classView.getSelectedClass(className);
                    Subject selectedSubject = subjectView.getSelectedTreeItem();

                    boolean success;
                    if (selectedSubject.isLeaf()) {

                        success = associationModel.addAssociation(new Association(selectedClass, selectedSubject));
                        if(success){
                            subjectTreeModel.setSubjectAssociated(selectedSubject, true);
                        }


                    } else {

                        childrenList = subjectTreeModel.getChildrenFromNodePostOrder(selectedSubject);
                        for (Subject s : childrenList) {

                            success = associationModel.addAssociation(new Association(selectedClass, s));

                            if (success) {
                                System.out.println("Association CREATED!!");
                                System.out.println(s.getName());
                                subjectTreeModel.setSubjectAssociated(s, true);

                            } else {
                                System.out.println("Association CANNOT BE CREATED with: "+s.getName());
                            }
                        }
                    }
                    subjectTreeModel.updateTreeColorAssociation(selectedSubject);
                    subjectView.getTreeView().refresh();
                }


            }
            if(((Button)e.getTarget()).getId().equals("disconnect_button")){

                if(classView.getSelectedClassByString() != null && subjectView.getSelectedTreeItem() != null){

                    String className = classView.getSelectedClassByString();
                    Class selectedClass = classView.getSelectedClass(className);
                    Subject selectedSubject = subjectView.getSelectedTreeItem();

                    boolean success;
                    if (selectedSubject.isLeaf()){

                        success = associationModel.removeAssociation(selectedSubject,selectedClass);

                        if(success)
                            subjectTreeModel.setSubjectAssociated(selectedSubject,false);

                    }else{

                        ArrayList<Subject> childrenList = subjectTreeModel.getChildrenFromNodePostOrder(selectedSubject);

                        for(Subject s: childrenList){

                            success = associationModel.removeAssociation(s,selectedClass);

                            if(success)
                                subjectTreeModel.setSubjectAssociated(s,false);

                        }
                    }
                    subjectTreeModel.updateTreeColorAssociation(selectedSubject);
                    subjectView.updateTree();
                }
            }
        }

    }

    public void addAssociationModel(AssociationModel model){

        this.associationModel = model;
    }

    public void addSubjectTreeModel(SubjectTreeModel model){

        this.subjectTreeModel = model;
    }
    public void addClassListView(ClassListView view){

        this.classView = view;
    }

    public void addSubjectTreeView(SubjectTreeView view){

        this.subjectView = view;
    }

}