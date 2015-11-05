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
import java.util.Optional;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class SubjectTreeView extends VBox{

    private TreeView<Subject> treeView;
    private TreeItem<Subject> rootNode = new TreeItem<>();
    private DialogBox addDialogBox;
    private final ContextMenu contextMenu = new ContextMenu();

    public SubjectTreeView(){

        init();
    }

    private void init(){

        rootNode.setExpanded(true);
        rootNode.setValue(new Subject("Root", "Root Desc"));
        treeView = new TreeView<>((TreeItem<Subject>) rootNode);
        treeView.setPrefSize(400,900);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<Subject> p) -> new CustomTreeCell());
        addDialogBox = new DialogBox(true);
        this.getChildren().add(treeView);
    }

    public ContextMenu getContextMenu(){
        return contextMenu;
    }

    public Subject getSelectedTreeItem(){
        //treeView
        return treeView.getSelectionModel().getSelectedItem().getValue();
    }
    private class CustomTreeCell extends TreeCell<Subject> {//MUST BE OF TYPE SUBJECT


        private TextField textField;

        public CustomTreeCell(){

            //Initialize
            init();
        }
        private void init(){

            //Add items to ContextMenu and set ID's for items
            if(contextMenu.getItems().isEmpty()) {
                MenuItem addItem = new MenuItem("Add");
                addItem.setId("add_menu_item");
                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setId("delete_menu_item");

                contextMenu.getItems().addAll(addItem, deleteItem);
            }

        }
        @Override
        public void startEdit(){

            /*super.startEdit();
            System.out.println("Start edit");
            if(textField == null)
                createTextField();

            setText(null);
            setGraphic(textField);
            textField.selectAll();*/
            String oldName = getText();
            String newName = null;
            String newDesc = null;
            addDialogBox.setClassTextField(oldName);
            addDialogBox.setDescTextField("");
            Optional<ButtonType> result = addDialogBox.showAndWait();

            if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){


                newName = addDialogBox.getClassName();
                newDesc = addDialogBox.getDesc();
                //model.modifyClass(oldName,newName,newDesc);
                //model.addClassToList(new Class(name, desc));
            }else{
                //setText(oldName);
                this.cancelEdit();
            }
        }

        @Override
        public void cancelEdit(){

            super.cancelEdit();

            setText(getItem().getName());
            setGraphic(getTreeItem().getGraphic());

        }

        @Override
        public void updateItem(Subject item, boolean empty){

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
                    //commitEdit(textField.getText());
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
            return (getItem() == null ? "" : getItem().getName());
        }
    }

}
