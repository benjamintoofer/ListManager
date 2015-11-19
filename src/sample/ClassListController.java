package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class ClassListController implements EventHandler<ActionEvent>, Serializable{

    private ClassListModel classListModel;
    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;

    private SubjectTreeView subjectTreeView;
    private ClassListView classListView;
    private transient DialogBox addClassDialogBox = new DialogBox("Class",true);
    private transient DialogBox removeClassDialogBox = new DialogBox("Class",false);

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

                    UserInterface.getTextArea().setText("Added Class: "+addClassDialogBox.getClassName());
                }


            }else if(((Button) e.getTarget()).getId().equals("remove_course_button")){

                if(classListView.getSelectedClassByString() != null){

                    String className = classListView.getSelectedClassByString();
                    Class c = classListModel.getClassByName(className);

                    StringBuilder listOfSubjects = new StringBuilder();
                    for(Association a : associationModel.queryByClass(c)){

                        listOfSubjects.append(a.getSubjectObj().getName()+", ");
                    }
                    if(listOfSubjects.length() > 0){
                        listOfSubjects.replace(listOfSubjects.length()-2,listOfSubjects.length(),"");
                    }
                    removeClassDialogBox.setHeaderText(classListView.getSelectedClassByString());
                    removeClassDialogBox.setContentText("Will disconnect with Subjects:\n"+listOfSubjects.toString());
                    Optional<ButtonType> result = removeClassDialogBox.showAndWait();

                    if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){



                        ArrayList<Association> list = associationModel.queryByClass(c);
                        for(Association a: list){
                            subjectTreeModel.setSubjectAssociated(a.getSubjectObj(),false);
                            subjectTreeModel.updateTreeColorAssociation(a.getSubjectObj());
                            subjectTreeView.updateTree();
                        }
                        associationModel.removeAssociation(c);
                        classListModel.removeClassFromList(classListView.getSelectedClassByString());

                        UserInterface.getTextArea().setText("Deleted Class: "+className);
                    }
                }

            }
        }
    }

    public void addClassListView(ClassListView view){

        classListView = view;
    }

    public void addAssociationModel(AssociationModel model){

        associationModel = model;
    }

    public void addSubjectTreeModel(SubjectTreeModel model){

        subjectTreeModel = model;
    }

    public void addSubjectTreeView(SubjectTreeView view){

        subjectTreeView = view;
    }
    public void addClassListModel(ClassListModel model){

        classListModel = model;
    }
}
