package sample;

/**
 * Created by benjamintoofer on 11/1/15.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class ClassListView extends VBox implements Serializable{

    private static ListView<String> listView;
    private ClassListModel model;
    private transient DialogBox dialog;
    private  VBox vBox;
    private transient HBox buttonBox;
    private transient Button addCourseButton, removeCourseButton,connectButton,disconnectButton;

    public ClassListView(){

        init();
    }

    /*
        Initialization
     */
    private void init(){

        //Instantiate listView
        listView =  new ListView<String>();
        listView.setEditable(true);
        listView.setPrefSize(300,900);
        listView.setCellFactory((ListView<String> l)->new CustomClassListCell());



        //Instantiate add and remove course buttons
        buttonBox = new HBox();

        //Add Course Button
        addCourseButton = new Button("+");
        addCourseButton.setId("add_course_button");
        addCourseButton.setPrefWidth(30);

        //Remove Course Button
        removeCourseButton = new Button("-");
        removeCourseButton.setId("remove_course_button");

        //Connect Button
        connectButton = new Button("Connect");
        connectButton.setId("connect_button");

        //Disconnect Button
        disconnectButton = new Button("Disconnect");
        disconnectButton.setId("disconnect_button");

        buttonBox.getChildren().addAll(addCourseButton,removeCourseButton,connectButton,disconnectButton);


        this.getChildren().addAll(listView, buttonBox);

        //////////
        dialog = new DialogBox("Class",true);
        dialog.setTitle("Class Information");


        ////////
    }

    public ListView<String> getListView(){

        return listView;
    }

    public void removeCourse(ArrayList<Class> list){

            listView.getItems().removeAll(listView.getItems());
            ArrayList<String> newList = new ArrayList<>();
            for(Class c: list){

                newList.add(c.getClassName());
            }
            listView.getItems().addAll(newList);


    }

    /*
        Getters for Buttons:

            - addCourseButton
            - removeCourseButton
            - connectButton
            - disconnectButton
     */
    public Button getAddCourseButton(){

        return addCourseButton;
    }

    public Button getRemoveCourseButton(){

        return removeCourseButton;
    }

    public Button getConnectButton(){

        return connectButton;
    }

    public Button getDisconnectButton(){

        return disconnectButton;
    }

    public String getSelectedClassByString(){

        return listView.getSelectionModel().getSelectedItem();
    }

    public Class getSelectedClass(String name){

        return model.getClassByName(name);
    }
    public void addClasses(ArrayList<Class> list){

        listView.getItems().removeAll(listView.getItems());
        ArrayList<String> newList = new ArrayList<String>();
        for(Class c: list){

            newList.add(c.getClassName());
        }
        listView.getItems().addAll(newList);
        listView.refresh();
    }

    public void addModel(ClassListModel model){

        this.model = model;
    }

    /*
        Update View when opening file
     */
    public void loadViewFromModel(ClassListModel model){

        this.addClasses(model.getClassList());
    }

    /*
        Refresh View
     */
    public void updateView(){

        listView.refresh();
    }

    /*
        Custom class for setting List View's Cell Factory
        (Allow more customization for List View)

        ****Must Override startEdit, cancelEdit, and updateEdit****
     */
    private class CustomClassListCell extends ListCell<String>{

        private TextField textField;

        public CustomClassListCell(){

        }

        @Override
        public void startEdit(){

            super.startEdit();

            String oldName = getText();
            String newName = null;
            String newDesc = null;
            dialog.setClassTextField(oldName);
            dialog.setDescTextField(model.getDescByClass(oldName));
            Optional<ButtonType> result = dialog.showAndWait();


            if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                newName = dialog.getClassName();
                newDesc = dialog.getDesc();
                model.modifyClass(oldName,newName,newDesc);
            }else{
                this.cancelEdit();
            }

        }

        @Override
        public void cancelEdit(){

            super.cancelEdit();

            setText((String) getItem());

        }

        @Override
        public void updateItem(String item, boolean empty){

            super.updateItem(item, empty);

            if(empty){
                setText(null);
                setGraphic(null);
            }else{
                if(isEditing()) {
                    if(textField != null){
                        textField.setText(getString());
                    }
                    setText(null);
                }
                else{
                    setText(getString());
                    setGraphic(null);
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

        private String getString(){

            return (getItem() == null ? "" : getItem().toString());
        }

    }

}
