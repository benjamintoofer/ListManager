package sample;

/**
 * Created by benjamintoofer on 10/29/15.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class UserInterface extends BorderPane{

    private int winWidth;
    private int winHeight;

    //Menu Bar
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem saveMenuItem;
    private MenuItem saveAsMenuItem;
    private MenuItem openMenuItem;

    //TreeView
    private TreeView<String> treeView;

    //ListView
    //private ListView<String> classListView;
    private ClassListView classListView;

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
        ///////Temp////////
        List<Employee> employees = Arrays.<Employee>asList(
                new Employee("Jacob Smith", "Accounts Department"),
                new Employee("Isabella Johnson", "Accounts Department"),
                new Employee("Ethan Williams", "Sales Department"),
                new Employee("Emma Jones", "Sales Department"),
                new Employee("Michael Brown", "Sales Department"),
                new Employee("Anna Black", "Sales Department"),
                new Employee("Rodger York", "Sales Department"),
                new Employee("Susan Collins", "Sales Department"),
                new Employee("Mike Graham", "IT Support"),
                new Employee("Judy Mayer", "IT Support"),
                new Employee("Gregory Smith", "IT Support"));
        TreeItem<String> rootNode = new TreeItem<>("Root");


        rootNode.setExpanded(true);
        /*for (Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem depNode = new TreeItem(employee.getDepartment());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }*/


        //rootNode.setExpanded(true);
        ////////Temp////////
        treeView = new TreeView<>((TreeItem<String>) rootNode);
        treeView.setPrefSize(winWidth/2,winHeight/2);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<String> p) -> new CustomTreeCell());

        //treeView.
        this.setLeft(treeView);

        //Instantiate classListView
        classListView = new ClassListView();
        //ObservableList<String> tempItems = FXCollections.observableArrayList("COMP2730","COMP2400","COMP3351","COMP3381");
        //classListView.setItems(tempItems);

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

    private class CustomTreeCell extends  TreeCell<String>{

        private final ContextMenu contextMenu = new ContextMenu();
        private TextField textField;

        public CustomTreeCell(){

            //Initialize
            init();
        }
        private void init(){
            //Initialize Context Menu
            MenuItem addItem = new MenuItem("Add");
            addItem.setId("add_menu_item");
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setId("delete_menu_item");

            contextMenu.getItems().addAll(addItem, deleteItem);
            contextMenu.setOnAction(new ContextMenuHandler());

        }
        @Override
        public void startEdit(){

            super.startEdit();
            System.out.println("Start edit");
            if(textField == null)
                createTextField();

            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit(){

            super.cancelEdit();

            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());

        }

        @Override
        public void updateItem(String item, boolean empty){

            super.updateItem(item,empty);

            if(empty){
                setText(null);
                setGraphic(null);
            }else{
                if(isEditing()){
                    if(textField != null){
                        textField.setText(getString());
                    }
                    setText(null);
                }else{
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());

                    if (!getTreeItem().isLeaf() && getTreeItem().getParent()!= null){
                        setContextMenu(contextMenu);
                    }else if(getTreeItem().isLeaf()){
                        setContextMenu(contextMenu);
                    }
                }
            }
        }

        private void createTextField(){

            textField = new TextField(getString());
            textField.setOnKeyReleased((KeyEvent t) ->{
                        if(t.getCode() == KeyCode.ENTER){
                            commitEdit(textField.getText());
                        }else if(t.getCode() == KeyCode.ESCAPE){
                            cancelEdit();
                        }
                    } );
        }

        private void displayInfoOfSelectedItem(){

            if(isSelected())
                System.out.println("Selected");
        }

        private String getString(){
            return (getItem() == null ? "" : getItem().toString());
        }
    }

    private class ContextMenuHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent e)
        {


            if(e.getTarget().getClass().toString().equals("class javafx.scene.control.MenuItem")){

                if(((MenuItem)e.getTarget()).getId().equals("add_menu_item"))
                    System.out.println("Adding");
                else if(((MenuItem) e.getTarget()).getId().equals("delete_menu_item")){
                    System.out.println("Deleteing");
                }
            }
        }
    }


    public static class Employee {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getDepartment() {
            return department.get();
        }

        public void setDepartment(String fName) {
            department.set(fName);
        }
    }


}
