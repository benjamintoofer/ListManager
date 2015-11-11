package sample;

/**
 * Created by benjamintoofer on 10/29/15.
 */





import java.io.Serializable;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
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
    private transient MenuItem showAssociatedMenuItem;
    private transient MenuItem showUnAssociatedMenuItem;
    private transient MenuItem saveMenuItem;
    private transient MenuItem saveAsMenuItem;
    private transient MenuItem openMenuItem;
    private transient MenuItem exportMenuItem;


    //Console View
    private transient TextArea textArea;


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
        saveAsMenuItem = new MenuItem("Save As...");
        saveAsMenuItem.setId("save_as_item");
        openMenuItem = new MenuItem("Open...");
        openMenuItem.setId("open_item");
        exportMenuItem = new MenuItem("Export...");
        exportMenuItem.setId("export_item");
        showAssociatedMenuItem = new MenuItem("Show Associated");
        showAssociatedMenuItem.setId("show_associated_item");
        showUnAssociatedMenuItem = new MenuItem("Show Unassociated");
        showUnAssociatedMenuItem.setId("show_unassociated_item");

        //Instantiate Menu
        fileMenu = new Menu("File");
        fileMenu.setVisible(true);
        viewMenu = new Menu("View");
        viewMenu.setVisible(true);


        //Instantiate Menu Bar
        menuBar = new MenuBar();
        menuBar.setVisible(true);

        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, saveAsMenuItem,exportMenuItem);
        viewMenu.getItems().addAll(showAssociatedMenuItem,showUnAssociatedMenuItem);

        this.setPrefSize(winWidth, winHeight);
        menuBar.setPrefSize(winWidth,40);
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
        textArea.setPrefSize(winWidth,winHeight*.3);

        this.setBottom(textArea);
        this.addKeyEventHandler(new KeyEventHandler());
        this.addViewOptionController(new ViewOptionHandler());


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
    }

    private void addViewOptionController(ViewOptionHandler controller){

        this.showAssociatedMenuItem.setOnAction(controller);
        this.showUnAssociatedMenuItem.setOnAction(controller);
    }

    public void addKeyEventHandler(KeyEventHandler eventHandler){

        this.addEventHandler(KeyEvent.KEY_RELEASED,eventHandler);
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
                textArea.setText("Association just created between Class: "+className+" with Subject: "+subjectName);

            }
        }
    }

    /*
        KeyEventHandler handles key events that occur on the stage
        Handles:
            1. ctrl+i = show information for Class or Subject or both
            2. esc    = deselect selected item from Class List and Subject Tree
     */
    public class KeyEventHandler implements EventHandler<KeyEvent> {

        final KeyCombination combo = new KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);

        @Override
        public void handle(KeyEvent e)
        {
            if(combo.match(e)){

                StringBuilder newString = new StringBuilder();
                Subject selectedSubject = subjectTreeView.getSelectedTreeItem();
                String tempClass = classListView.getSelectedClassByString();
                Class selectedClass = classListModel.getClassByName(tempClass);

                if( selectedSubject != null){
                    System.out.println("Subject Displayed");

                    ArrayList<Association> list = associationModel.queryBySubject(selectedSubject);
                    newString.append("Subject: "+selectedSubject.getName()+"\n\n");
                    newString.append("Description: "+selectedSubject.getDescription()+"\n\n");
                    newString.append("Associations: ");
                    for(Association a: list){
                        newString.append(a.getClassObj().getClassName()+", ");
                    }
                    newString.append("\n----------------------------------------------------------------------------------------------\n");
                }

                if(selectedClass != null){
                    System.out.println("Class Displayed");

                    ArrayList<Association> list = associationModel.queryByClass(selectedClass);
                    newString.append("Class: "+ selectedClass.getClassName()+"\n\n");
                    newString.append("Description: "+selectedClass.getClassDescription()+"\n\n");
                    newString.append("Associations: ");
                    for(Association a : list){
                        newString.append(a.getSubjectObj().getName()+", ");
                    }
                    newString.append("\n----------------------------------------------------------------------------------------------\n");
                }

                textArea.setText(newString.toString());
            }

            /*
                Deselect from SubjectView and ClassView
             */
            if(e.getCode().equals(KeyCode.ESCAPE)){

                subjectTreeView.getTreeView().getSelectionModel().select(null);
                classListView.getListView().getSelectionModel().select(null);
            }

        }
    }

    public class ViewOptionHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent e)
        {

            if(((MenuItem)e.getTarget()).getId().equals("show_unassociated_item")){

                subjectTreeView.expandAssociatedNodes(false);
            }

            if(((MenuItem)e.getTarget()).getId().equals("show_associated_item")){

                subjectTreeView.expandAssociatedNodes(true);
            }
        }
    }


}
