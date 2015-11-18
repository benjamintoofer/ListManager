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
                            subjectTreeModel.updateTreeColorAssociation(selectedSubject);
                        }else{
                            UserInterface.getTextArea().setText("Association already made with "+selectedClass.getClassName()+ " and "+selectedSubject.getName());
                        }


                    } else {
                        StringBuilder strBuild = new StringBuilder();
                        childrenList = subjectTreeModel.getLeavesFromNode(selectedSubject);
                        for (Subject s : childrenList) {

                            success = associationModel.addAssociation(new Association(selectedClass, s));

                            if (success) {
                                System.out.println("Association CREATED with "+s.getName());
                                subjectTreeModel.setSubjectAssociated(s, true);
                                subjectTreeModel.updateTreeColorAssociation(s);
                                System.out.println("Updated tree with new association!!");
                                strBuild.append("Association created with "+selectedClass.getClassName() + " and "+s.getName()+"\n");

                            } else {

                                strBuild.append("Association already made with " + selectedClass.getClassName() + " and " + selectedSubject.getName() + "\n");
                                System.out.println("Association CANNOT BE CREATED with: "+s.getName());
                            }
                        }

                        UserInterface.getTextArea().setText(strBuild.toString());
                    }

                    subjectView.getTreeView().refresh();
                }else{

                    UserInterface.getTextArea().setText("Must select a Class and a Subject");
                }

                System.out.println(subjectTreeModel.printTree());
            }
            if(((Button)e.getTarget()).getId().equals("disconnect_button")){

                if(classView.getSelectedClassByString() != null && subjectView.getSelectedTreeItem() != null){

                    String className = classView.getSelectedClassByString();
                    Class selectedClass = classView.getSelectedClass(className);
                    Subject selectedSubject = subjectView.getSelectedTreeItem();

                    boolean success;
                    if (selectedSubject.isLeaf()){

                        success = associationModel.removeAssociation(selectedSubject,selectedClass);

                        if(success) {
                            subjectTreeModel.setSubjectAssociated(selectedSubject, false);
                            subjectTreeModel.updateTreeColorAssociation(selectedSubject);
                            UserInterface.getTextArea().setText("Disconnected  "+selectedClass.getClassName() + " and "+selectedSubject.getName());
                        }else{
                            UserInterface.getTextArea().setText("No association with "+selectedClass.getClassName() + " and "+selectedSubject.getName());
                        }

                    }else{

                        StringBuilder stringBuilder = new StringBuilder();
                        ArrayList<Subject> childrenList = subjectTreeModel.getChildrenFromNodePostOrder(selectedSubject);

                        for(Subject s: childrenList){

                            success = associationModel.removeAssociation(s,selectedClass);
                            if(success) {

                                subjectTreeModel.setSubjectAssociated(s, false);
                                subjectTreeModel.updateTreeColorAssociation(s);
                                stringBuilder.append("Disconnected  "+selectedClass.getClassName() + " and "+s.getName()+"\n");
                            }else{
                                stringBuilder.append("No association with "+selectedClass.getClassName() + " and "+s.getName()+"\n");
                            }

                        }

                        UserInterface.getTextArea().setText(stringBuilder.toString());
                    }

                    subjectView.updateTree();
                }else{

                    UserInterface.getTextArea().setText("Must select a Class and a Subject");
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
