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
    private transient DialogBox dialogBox;
    private transient Stage stage;

    private ClassListModel classListModel;
    private AssociationModel associationModel;
    private SubjectTreeModel subjectTreeModel;
    private SubjectTreeView subjectTreeView;
    private ClassListView classListView;
    private RunListManager rlm;

    public IOController(Stage stage){

        this.stage = stage;
        init();
    }
    /*
        Initialization
     */
    private void init(){

        fileChooser = new FileChooser();
        dialogBox = new DialogBox("",false);
    }

    @Override
    public void handle(ActionEvent e)
    {

        /*
            Handle Exporting
         */
        if(((MenuItem)e.getTarget()).getId().equals("export_item")){

            File fileToExport = fileChooser.showSaveDialog(stage);

            /*try{

            }catch(){

            }*/

            System.out.println("EXPORTING");
        }

        /*
        Handle Save As File
         */
        if(((MenuItem)e.getTarget()).getId().equals("save_as_item")){

            //Display Save Dialog of FileChooser
            fileChooser.setTitle("Save File");
            File fileToSave = fileChooser.showSaveDialog(stage);

            if(fileToSave != null){

                savedPath = fileToSave.getAbsolutePath();
                //Save file
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

        /*
            Handle Open File
         */
        if(((MenuItem)e.getTarget()).getId().equals("open_item")){

            //Display Open Dialog of FileChooser
            fileChooser.setTitle("Open File");
            File fileToOpen = fileChooser.showOpenDialog(stage);

            if(fileToOpen != null){

                savedPath = fileToOpen.getAbsolutePath();

                try{
                    FileInputStream fileIn = new FileInputStream(savedPath);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    rlm = (RunListManager)in.readObject();

                    //Assign the loaded models
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
                    System.out.println("Model objects not found");
                    c.printStackTrace();
                    return;
                }

            }
        }
    }

    /*
    Add ClassListModel
     */
    public void addClassListModel(ClassListModel model){

        classListModel = model;
    }

    /*
    Add SubjectTreeModel
     */
    public void addSubjectTreeModel(SubjectTreeModel model){

        subjectTreeModel = model;
    }

    /*
    Add SubjectTreeView
     */
    public void addSubjectTreeView(SubjectTreeView view){

        subjectTreeView = view;
    }

    /*
    Add ClassListView
     */
    public void addClassListView(ClassListView view){

        classListView = view;
    }

    /*
    Add RunListManager
     */
    public void addRunListManager(RunListManager rlm){

        this.rlm = rlm;
    }
}
