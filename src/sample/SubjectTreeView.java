package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class SubjectTreeView extends VBox{

    private TreeView<String> treeView;
    private TreeItem<String> rootNode = new TreeItem<>("Root");
    private final ContextMenu contextMenu = new ContextMenu();

    public SubjectTreeView(){

        init();
    }

    private void init(){

        rootNode.setExpanded(true);

        treeView = new TreeView<>((TreeItem<String>) rootNode);
        treeView.setPrefSize(400,900);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<String> p) -> new CustomTreeCell());

        this.getChildren().add(treeView);
    }

    public ContextMenu getContextMenu(){
        return contextMenu;
    }


    private class CustomTreeCell extends TreeCell<String> {


        private TextField textField;

        public CustomTreeCell(){

            //Initialize
            init();
        }
        private void init(){

            //Initialize Context Menu
            if(contextMenu.getItems().isEmpty()) {
                MenuItem addItem = new MenuItem("Add");
                addItem.setId("add_menu_item");
                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setId("delete_menu_item");

                contextMenu.getItems().addAll(addItem, deleteItem);
            }
            //contextMenu.setOnAction(new ContextMenuHandler());

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

    private class ContextMenuHandler implements EventHandler<ActionEvent> {

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
}
