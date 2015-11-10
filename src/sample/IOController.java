package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Observable;

/**
 * Created by benjamintoofer on 11/8/15.
 */
public class IOController extends Observable implements EventHandler<ActionEvent>, Serializable {

    private String savedPath = null;
    private transient FileChooser fileChooser;
    private transient Stage stage;

    private ClassListModel classListModel;
    private AssociationModel associationModel;
    private SubjectTreeModel subjectTreeModel;
    private SubjectTreeView subjectTreeView;
    private ClassListView classListView;
    private RunListManager rlm;

    public IOController(Stage stage){

        this.stage = stage;
        fileChooser = new FileChooser();
    }

    @Override
    public void handle(ActionEvent e)
    {
        if(((MenuItem)e.getTarget()).getId().equals("save_as_item")){

            fileChooser.setTitle("Save File");
            File fileToSave = fileChooser.showSaveDialog(stage);

            if(fileToSave != null){

                savedPath = fileToSave.getAbsolutePath();
                //SAve file
                try{

                    FileOutputStream fileOut = new FileOutputStream(savedPath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(rlm);
                    out.close();
                    fileOut.close();

                }catch(IOException ex){

                    System.out.println(ex.getCause());
                    ex.printStackTrace();
                }

            }
        }

        if(((MenuItem)e.getTarget()).getId().equals("open_item")){

            fileChooser.setTitle("Save File");
            File fileToOpen = fileChooser.showOpenDialog(stage);

            if(fileToOpen != null){

                savedPath = fileToOpen.getAbsolutePath();

                try{
                    FileInputStream fileIn = new FileInputStream(savedPath);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    rlm = (RunListManager)in.readObject();

                    subjectTreeView.loadViewFromModel(rlm.getSubjectTreeModel());
                    classListView.loadViewFromModel(rlm.getClassListModel());
                    associationModel = rlm.getAssociationModel();

                    System.out.println("SUBJECT TREE:\n"+subjectTreeModel.printTree());
                    //System.out.println("ASSOCIATIONS LOADED:\n"+rlm.getAssociationModel().printAssociations());

                    in.close();
                    fileIn.close();

                }catch(IOException ex){

                    ex.printStackTrace();

                }catch(ClassNotFoundException c)
                {
                    System.out.println("Employee class not found");
                    c.printStackTrace();
                    return;
                }

            }
        }
    }

    public void addClassListModel(ClassListModel model){

        classListModel = model;
    }

    public void addSubjectTreeModel(SubjectTreeModel model){

        subjectTreeModel = model;
    }

    public void addSubjectTreeView(SubjectTreeView view){

        subjectTreeView = view;
    }

    public void addClassListView(ClassListView view){

        classListView = view;
    }

    public void addRunListManager(RunListManager rlm){

        this.rlm = rlm;
    }
}
