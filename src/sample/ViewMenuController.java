package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by benjamintoofer on 11/13/15.
 */
public class ViewMenuController implements EventHandler<ActionEvent>,Serializable{


    private SubjectTreeView subjectTreeView;
    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;
    private ClassListView classListView;
    private ClassListModel classListModel;


    public ViewMenuController(){

    }

    @Override
    public void handle(ActionEvent e)
    {
        /*
            Expand the subject tree of only unassociated leaf nodes
         */
        if(((MenuItem)e.getTarget()).getId().equals("show_unassociated_item")){

            subjectTreeView.expandAssociatedNodes(false);
        }

        /*
            Expand the subject tree of only associated leaf nodes
         */
        if(((MenuItem)e.getTarget()).getId().equals("show_associated_item")){

            subjectTreeView.expandAssociatedNodes(true);
        }

        if(((MenuItem)e.getTarget()).getId().equals("show_class_connection")){

            if(classListView.getSelectedClassByString() != null){

                String strClass = classListView.getSelectedClassByString();
                Class selectedClass = classListView.getSelectedClass(strClass);

                ArrayList<Association> list = associationModel.queryByClass(selectedClass);
                ArrayList<Subject> subjectList = new ArrayList<Subject>();

                for(Association a : list){

                    subjectList.add(a.getSubjectObj());
                }

                subjectTreeView.expandRequestedNodes(subjectList);
                UserInterface.getTextArea().setText("Showing Class: "+selectedClass.getClassName()+" connections");


            }else{

                UserInterface.getTextArea().setText("Must select a class");
            }
        }

        if(((MenuItem)e.getTarget()).getId().equals("show_subject_connection")){

            if(subjectTreeView.getSelectedTreeItem() != null){

                ArrayList<Association> list;
                ArrayList<Subject> subjectLeafList;
                ArrayList<Class> classList = new ArrayList<Class>();

                if(subjectTreeView.getSelectedTreeItem().isLeaf()){

                    list = associationModel.queryBySubject(subjectTreeView.getSelectedTreeItem());

                    for(Association a : list){

                        classList.add(a.getClassObj());
                    }

                }else{

                    subjectLeafList = subjectTreeModel.getLeavesFromNode(subjectTreeView.getSelectedTreeItem());

                    for(Subject s : subjectLeafList){
                        list = associationModel.queryBySubject(s);

                        for(Association a : list){

                            if(!classList.contains(a.getClassObj()))
                                classList.add(a.getClassObj());
                        }

                    }
                }


                classListView.addClasses(classList);
                UserInterface.getTextArea().setText("Showing Subject: "+subjectTreeView.getSelectedTreeItem().getName()+" connections");

            }else{

                UserInterface.getTextArea().setText("Must select a subject");
            }

        }

        /*
            Display Information of selected subject and class in Text Area
         */
        if(((MenuItem)e.getTarget()).getId().equals("show_information_item")){

            StringBuilder newString = new StringBuilder();
            Subject selectedSubject = subjectTreeView.getSelectedTreeItem();
            String tempClass = classListView.getSelectedClassByString();
            Class selectedClass = classListModel.getClassByName(tempClass);

            if( selectedSubject != null){

                ArrayList<Association> list = associationModel.queryBySubject(selectedSubject);
                newString.append("Subject: "+selectedSubject.getName()+"\n\n");
                newString.append("Description: "+selectedSubject.getDescription()+"\n\n");
                newString.append("Associations: ");
                for(Association a: list){
                    newString.append(a.getClassObj().getClassName()+", ");
                }
                newString.replace(newString.length()-2,newString.length(),"");
                newString.append("\n----------------------------------------------------------------------------------------------\n");
            }

            if(selectedClass != null){

                ArrayList<Association> list = associationModel.queryByClass(selectedClass);
                newString.append("Class: "+ selectedClass.getClassName()+"\n\n");
                newString.append("Description: "+selectedClass.getClassDescription()+"\n\n");
                newString.append("Associations: ");
                for(Association a : list){
                    newString.append(a.getSubjectObj().getName()+", ");
                }
                newString.replace(newString.length()-2,newString.length(),"");
                newString.append("\n----------------------------------------------------------------------------------------------\n");
            }

            UserInterface.getTextArea().setText(newString.toString());

        }

        if(((MenuItem)e.getTarget()).getId().equals("reset_item")){

            subjectTreeView.getTreeView().getSelectionModel().select(null);
            classListView.getListView().getSelectionModel().select(null);
            classListView.addClasses(classListModel.getClassList());
        }
    }

    public void addSubjectTreeView(SubjectTreeView view){

        subjectTreeView = view;
    }

    public void addClassListView(ClassListView view){

        classListView = view;
    }

    public void addAssociationModel(AssociationModel model){

        associationModel = model;
    }

    public void addClassListModel(ClassListModel model){

        classListModel = model;
    }

    public void addSubjectTreeModel(SubjectTreeModel model){

        subjectTreeModel = model;
    }
}
