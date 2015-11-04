package sample;

/**
 * Created by benjamintoofer on 10/29/15.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class UserInterface extends BorderPane implements Observer{

    private int winWidth;
    private int winHeight;

    //Views
    private ClassListView classListView;
    private SubjectTreeView subjectTreeView;

    //Models
    private ClassListModel classListModel;
    private SubjectTreeModel subjectTreeModel;

    //Menu Bar
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem saveMenuItem;
    private MenuItem saveAsMenuItem;
    private MenuItem openMenuItem;

    //TreeView
    private TreeView<String> treeView;




    //Console View
    private TextArea textArea;

    public UserInterface(int width,int height){

        super();
        this.winWidth = width;
        this.winHeight = height;

        init();
    }

    private void init(){

        //Instantiate Menu Items
        saveMenuItem = new MenuItem("Save");
        saveAsMenuItem = new MenuItem("Save As...");
        openMenuItem = new MenuItem("Open...");

        //Instantiate Menu
        fileMenu = new Menu("File");
        fileMenu.setVisible(true);


        //Instantiate Menu Bar
        menuBar = new MenuBar();
        menuBar.setVisible(true);
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, saveAsMenuItem);


        this.setPrefSize(winWidth, winHeight);
        menuBar.setPrefSize(winWidth,40);
        this.setTop(menuBar);
        this.setPrefSize(winWidth,winHeight);
        menuBar.getMenus().addAll(fileMenu);

        //Instantiate Tree View
        subjectTreeView = new SubjectTreeView();
        this.setLeft(subjectTreeView);

        //Instantiate classListView
        classListView = new ClassListView();


        this.setRight(classListView);

        //Instantiate Text Area
        textArea = new TextArea();

        this.setBottom(textArea);


    }
    public int getWinWidth()
    {
        return winWidth;
    }

    public int getWinHeight()
    {
        return winHeight;
    }



    public ClassListView getClassListView(){

        return classListView;
    }

    public SubjectTreeView getSubjectTreeView(){

        return subjectTreeView;
    }
    /*
        Add Class Model and Controller
     */
    public void addClassListModel(ClassListModel model){

        classListModel = model;
        classListView.addModel(classListModel);
    }

    public void addClassListController(ClassListController controller){

        classListView.getAddCourseButton().setOnAction(controller);
        classListView.getRemoveCourseButton().setOnAction(controller);
    }

    /*
        Add Subject Tree Model and Controller
     */
    public void addSubjectTreeModel(SubjectTreeModel model){

        subjectTreeModel = model;
    }

    public void addSubjectTreeController(SubjectTreeController controller){

        subjectTreeView.getContextMenu().setOnAction(controller);
    }



    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println(o.getClass().toString());
        if(o.getClass().toString().equals("class sample.ClassListModel")){
            if(arg.equals("add")){
                ArrayList<Class> newClassList = classListModel.getClassList();
                classListView.addClasses(newClassList);
            }
            if(arg.equals("remove")){
                ArrayList<Class> newClassList = classListModel.getClassList();
                classListView.removeCourse(newClassList);
            }

        }
    }




}
