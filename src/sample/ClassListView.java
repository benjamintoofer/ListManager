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

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class ClassListView extends VBox{

    private static ListView<String> listView;
    private ClassListModel model;
    private DialogBox dialog;
    private  VBox vBox;
    private HBox buttonBox;
    private Button addCourseButton, removeCourseButton,connectButton;

    public ClassListView(){

        init();
    }

    private void init(){

        //Instantiate listView
        listView =  new ListView<String>();
        listView.setEditable(true);
        listView.setPrefSize(300,900);
        ObservableList<String> tempItems = FXCollections.observableArrayList("COMP2730", "COMP2400", "COMP3351", "COMP3381");
        //listView.setItems(tempItems);
        listView.setCellFactory((ListView<String> l)->new CustomClassListCell());



        //Instantiate add and remove course buttons
        buttonBox = new HBox();
        addCourseButton = new Button("+");
        addCourseButton.setId("add_course_button");
        removeCourseButton = new Button("-");
        removeCourseButton.setId("remove_course_button");
        connectButton = new Button("Connect");
        connectButton.setId("connect_button");
        buttonBox.getChildren().addAll(addCourseButton,removeCourseButton,connectButton);


        this.getChildren().addAll(listView, buttonBox);

        //////////
        dialog = new DialogBox(true);
        dialog.setTitle("Class Information");


        ////////
    }

    public void removeCourse(ArrayList<Class> list){

            listView.getItems().removeAll(listView.getItems());
            ArrayList<String> newList = new ArrayList<>();
            for(Class c: list){

                newList.add(c.getClassName());
            }
            listView.getItems().addAll(newList);


    }
    public Button getAddCourseButton(){

        return addCourseButton;
    }

    public Button getRemoveCourseButton(){

        return removeCourseButton;
    }

    public String getSelectedClass(){
        return listView.getSelectionModel().getSelectedItem();
    }
    public void addClasses(ArrayList<Class> list){

        listView.getItems().removeAll(listView.getItems());
        ArrayList<String> newList = new ArrayList<>();
        for(Class c: list){

            newList.add(c.getClassName());
        }
        listView.getItems().addAll(newList);
        //System.out.println(model);
        System.out.println(model.getClassList().size());
    }

    public void addModel(ClassListModel model){
        this.model = model;
    }

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
