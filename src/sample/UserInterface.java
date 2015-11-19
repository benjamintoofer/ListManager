package sample;

/**
 * Created by benjamintoofer on 10/29/15.
 */





import java.io.Serializable;
import java.security.Key;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserInterface extends BorderPane implements Observer,Serializable{

    private int winWidth;
    private int winHeight;

    private transient Stage stage;

    //Views
    private ClassListView classListView;
    private SubjectTreeView subjectTreeView;

    //Models
    private ClassListModel classListModel;
    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;


    //Menu Bar
    private transient MenuBar menuBar;
    private transient Menu fileMenu;
    private transient Menu viewMenu;
    private transient Menu showConnectionsMenu;
    private transient MenuItem showSubjectConnectionMenuItem;
    private transient MenuItem showClassConnectionMenuItem;
    private transient MenuItem showAssociatedMenuItem;
    private transient MenuItem showUnAssociatedMenuItem;
    private transient MenuItem showInfoMenuItem;
    private transient MenuItem saveMenuItem;
    private transient MenuItem saveAsMenuItem;
    private transient MenuItem openMenuItem;
    private transient MenuItem exportMenuItem;
    private transient MenuItem importMenuItem;
    private transient MenuItem parseMenuItem;
    private transient MenuItem resetMenuItem;


    //Console View
    private static transient TextArea textArea;


    public UserInterface(int width,int height){

        super();
        this.winWidth = width;
        this.winHeight = height;

        init();
    }

    private void init(){


        //Instantiate Menu Items
        saveMenuItem = new MenuItem("Save");
        saveMenuItem.setId("save_item");
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,KeyCombination.SHORTCUT_DOWN));

        saveAsMenuItem = new MenuItem("Save As...");
        saveAsMenuItem.setId("save_as_item");
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN,KeyCombination.SHORTCUT_DOWN));

        openMenuItem = new MenuItem("Open...");
        openMenuItem.setId("open_item");

        exportMenuItem = new MenuItem("Export...");
        exportMenuItem.setId("export_item");

        importMenuItem = new MenuItem("Import...");
        importMenuItem.setId("import_item");

        parseMenuItem = new MenuItem("Parse...");
        parseMenuItem.setId("parse_item");

        showAssociatedMenuItem = new MenuItem("Show Associated");
        showAssociatedMenuItem.setId("show_associated_item");
        showAssociatedMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D,KeyCombination.CONTROL_DOWN));

        showUnAssociatedMenuItem = new MenuItem("Show Unassociated");
        showUnAssociatedMenuItem.setId("show_unassociated_item");
        showUnAssociatedMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F,KeyCombination.CONTROL_DOWN));

        showClassConnectionMenuItem = new MenuItem("Show Class Connection");
        showClassConnectionMenuItem.setId("show_class_connection");
        showClassConnectionMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.J,KeyCombination.CONTROL_DOWN));

        showSubjectConnectionMenuItem = new MenuItem("Show Subject Connection");
        showSubjectConnectionMenuItem.setId("show_subject_connection");
        showSubjectConnectionMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN));

        showInfoMenuItem = new MenuItem("View Information");
        showInfoMenuItem.setId("show_information_item");
        showInfoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I,KeyCombination.SHORTCUT_DOWN));

        resetMenuItem = new MenuItem("Reset");
        resetMenuItem.setId("reset_item");
        resetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE,KeyCombination.SHIFT_DOWN));

        //Instantiate Menu
        fileMenu = new Menu("File");
        fileMenu.setVisible(true);
        viewMenu = new Menu("View");
        viewMenu.setVisible(true);
        showConnectionsMenu = new Menu("Show Connections");
        showConnectionsMenu.setVisible(true);


        //Instantiate Menu Bar
        menuBar = new MenuBar();
        menuBar.setVisible(true);

        showConnectionsMenu.getItems().addAll(showClassConnectionMenuItem,showSubjectConnectionMenuItem);
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, saveAsMenuItem,importMenuItem,exportMenuItem,parseMenuItem);
        viewMenu.getItems().addAll(showInfoMenuItem,showConnectionsMenu,showUnAssociatedMenuItem,showAssociatedMenuItem,resetMenuItem);


        this.setPrefSize(winWidth, winHeight);
        menuBar.setPrefSize(winWidth,30);
        this.setTop(menuBar);
        this.setPrefSize(winWidth,winHeight);
        menuBar.getMenus().addAll(fileMenu,viewMenu);

        //Instantiate Tree View
        subjectTreeView = new SubjectTreeView();
        this.setLeft(subjectTreeView);

        //Instantiate classListView
        classListView = new ClassListView();


        this.setRight(classListView);

        //Instantiate Text Area
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        textArea.setPrefSize(winWidth,winHeight*.3);

        this.setBottom(textArea);


    }
    public SubjectTreeModel getModel(){

        return subjectTreeModel;
    }
    public void addStage(Stage stage){

        this.stage = stage;
    }
    /*
        Getter for Window dimmensions
     */
    public int getWinWidth()
    {
        return winWidth;
    }

    public int getWinHeight()
    {
        return winHeight;
    }
    //////////////////////////////////////////////////////
    /*
        Getter for ClassListView and SubjectTreeView
     */
    public ClassListView getClassListView(){

        return classListView;
    }

    public SubjectTreeView getSubjectTreeView(){

        return subjectTreeView;
    }
    //////////////////////////////////////////////////////
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
    //////////////////////////////////////////////////////
    /*
        Add Subject Tree Model and Controller
     */
    public void addSubjectTreeModel(SubjectTreeModel model){

        subjectTreeModel = model;
        subjectTreeView.addModel(model);
    }

    public void addSubjectTreeController(SubjectTreeController controller){

        subjectTreeView.getContextMenu().setOnAction(controller);
    }
    //////////////////////////////////////////////////////
    /*
        Add Association Model and Controller
     */
    public void addAssociationController(AssociationController controller){

        classListView.getConnectButton().setOnAction(controller);
        classListView.getDisconnectButton().setOnAction(controller);
    }

    public void addAssociationModel(AssociationModel model){

        associationModel = model;
    }

    public void addIOControllerToFileMenu(IOController controller){

        saveAsMenuItem.setOnAction(controller);
        saveMenuItem.setOnAction(controller);
        openMenuItem.setOnAction(controller);
        exportMenuItem.setOnAction(controller);
        importMenuItem.setOnAction(controller);
        parseMenuItem.setOnAction(controller);
    }

    public void updateView(){

        classListView.updateView();
        subjectTreeView.updateTree();
    }

    /*
        Updated from Observables:

         o = Observable object:
             1.SubjectTreeModel
            2.ClassListModel
         arg = Optional argument passed when observable notifies obevers
     */
    @Override
    public void update(Observable o, Object arg)
    {

        /*
            Update Class list view when change has occurred in ClassListModel

           1. Check of observable object that notified is the ClassListModel
           2. Determine argument, whether the ClassListView must
                - add an item to its list
                    (modifying an a item occurs here as well)
                - remove an item from its list
         */
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

        /*
            Update Subject tree view when change has occurred in Subject tree model

           1. Check of observable object that notified is the SubjectTreeModel
           2. Determine argument, whether the SubjectTreeView must
                - add an item to its tree
                - remove an item from its tree
                - modify an item in the tree
         */
        if(o.getClass().toString().equals("class sample.SubjectTreeModel")){
            if(arg.equals("add")){

                subjectTreeView.addChildToTreeView(subjectTreeModel.getChildToAdd());
                System.out.println(subjectTreeModel.printTree());

            }
            if(arg.equals("remove")){

                subjectTreeView.removeChildFromTreeView(subjectTreeModel.getChildToRemove());
                System.out.println(subjectTreeModel.printTree());
            }

            if(arg.equals("modify")){

                subjectTreeView.modifyChildFromTreeView(subjectTreeModel.getChildToModify());
                subjectTreeView.updateTree();
                System.out.println(subjectTreeModel.printTree());
            }
        }

        if(o.getClass().toString().equals("class sample.AssociationModel")){

            if(arg.equals("add")){

                String className = associationModel.getAddedAssociation().getClassObj().getClassName();
                String subjectName = associationModel.getAddedAssociation().getSubjectObj().getName();
                subjectTreeView.updateTree();
                textArea.setText("Association just created");

            }
        }
    }

    public void addViewMenuController(ViewMenuController viewMenuController){

        showAssociatedMenuItem.setOnAction(viewMenuController);
        showUnAssociatedMenuItem.setOnAction(viewMenuController);
        showInfoMenuItem.setOnAction(viewMenuController);
        showClassConnectionMenuItem.setOnAction(viewMenuController);
        showSubjectConnectionMenuItem.setOnAction(viewMenuController);
        resetMenuItem.setOnAction(viewMenuController);

    }

    public static TextArea getTextArea(){

        return textArea;
    }
}
