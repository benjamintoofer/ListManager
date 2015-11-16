package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Observable;
import java.util.Optional;

/**
 * Created by benjamintoofer on 11/8/15.
 */
public class IOController extends Observable implements EventHandler<ActionEvent>, Serializable {

    private String savedPath = null;
    private transient FileChooser fileChooser;

    private transient Alert alertBox;
    private transient Stage stage;

    private ClassListModel classListModel;
    private AssociationModel associationModel;
    private SubjectTreeModel subjectTreeModel;
    private SubjectTreeView subjectTreeView;
    private ClassListView classListView;
    private static RunListManager rlm;

    public IOController(Stage stage){

        this.stage = stage;

        init();
    }
    /*
        Initialization
     */
    private void init(){

        fileChooser = new FileChooser();
        alertBox = new Alert(Alert.AlertType.INFORMATION);
    }

    @Override
    public void handle(ActionEvent e)
    {

        /*
            Handle Exporting
         */
        if(((MenuItem)e.getTarget()).getId().equals("export_item")){

            fileChooser.setTitle("Export File");
            File fileToExport = fileChooser.showSaveDialog(stage);

            if(fileToExport != null){

                try{

                    IOUtil.exportTxt(fileToExport,rlm);

                }catch(IOException ex){

                    alertBox.setHeaderText("File Error");
                    alertBox.setContentText("Error opening file "+fileToExport.getAbsolutePath());
                    alertBox.showAndWait();

                    System.err.println(ex.getMessage());
                }

                System.out.println("EXPORTING");
            }

        }

        /*
        Handle Save  File
         */
        if(((MenuItem)e.getTarget()).getId().equals("save_item")){

            if(savedPath == null){

                //Display Save Dialog of FileChooser
                fileChooser.setTitle("Save File");
                File fileToSave = fileChooser.showSaveDialog(stage);

                if(fileToSave != null){

                    savedPath = setFileType(fileToSave.getAbsolutePath(),".ser");

                }
            }

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

            UserInterface.getTextArea().setText("File "+savedPath+" saved");
        }

        /*
        Handle Save As File
         */
        if(((MenuItem)e.getTarget()).getId().equals("save_as_item")){

            //Display Save Dialog of FileChooser
            fileChooser.setTitle("Save File");
            File fileToSave = fileChooser.showSaveDialog(stage);

            if(fileToSave != null){

                savedPath = setFileType(fileToSave.getAbsolutePath(),".ser");
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

                if(checkFileType(savedPath,".ser")){

                    try{
                        FileInputStream fileIn = new FileInputStream(savedPath);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        rlm = (RunListManager)in.readObject();

                        subjectTreeModel = rlm.getSubjectTreeModel();
                        //Assign the loaded models
                        subjectTreeView.loadViewFromModel(rlm.getSubjectTreeModel());
                        classListView.loadViewFromModel(rlm.getClassListModel());

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

                    setChanged();
                    notifyObservers();

                }else{

                    alertBox.setHeaderText("File Error");
                    alertBox.setContentText("File " + savedPath + " incorrect file type\n\n Required file type of \".ser\"");
                    alertBox.showAndWait();

                }

            }
        }
    }

    private static String setFileType(String fileName ,String fileExt){

        int index = fileName.lastIndexOf(".");
        StringBuilder newFilePath = new StringBuilder(fileName);

        if(index == -1){

            newFilePath.append(fileExt);

        }else{
            String sub = fileName.substring(index);
            newFilePath.replace(index,newFilePath.length(),fileExt);
        }

        return newFilePath.toString();
    }

    private static boolean checkFileType(String fileName,String fileExt){

        boolean correctType = true;
        int index = fileName.lastIndexOf(".");

        if(index == -1){

            correctType = false;

        }else{
            String sub = fileName.substring(index);
            if(!sub.equals(fileExt))
                correctType = false;
        }

        return correctType;
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

    public SubjectTreeModel getSubjectTreeModel(){

        return rlm.getSubjectTreeModel();
    }

    public AssociationModel getAssociationModel(){

        return rlm.getAssociationModel();
    }

    public ClassListModel getClassListModel(){

        return rlm.getClassListModel();
    }
}
