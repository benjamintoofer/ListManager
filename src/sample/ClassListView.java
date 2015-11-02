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

public class ClassListView extends VBox{

    private static ListView<String> listView;
    private HBox buttonBox;
    private Button addCourseButton, removeCourseButton;

    public ClassListView(){

        init();
    }

    private void init(){

        //Instantiate listView
        listView = new ListView<>();
        listView.setEditable(true);
        listView.setPrefSize(300,900);
        ObservableList<String> tempItems = FXCollections.observableArrayList("COMP2730", "COMP2400", "COMP3351", "COMP3381");
        listView.setItems(tempItems);
        listView.setCellFactory((ListView<String> l)->new CustomClassListCell());



        //Instantiate add and remove course buttons
        buttonBox = new HBox();
        addCourseButton = new Button("+");
        addCourseButton.setId("add_course_button");
        removeCourseButton = new Button("-");
        removeCourseButton.setId("remove_course_button");
        buttonBox.getChildren().addAll(addCourseButton,removeCourseButton);




        this.getChildren().addAll(listView,buttonBox);
    }

    public boolean removeCourse(String course){

        if(!course.equals(null)){


            listView.getItems().remove(course);
        }

        //listView.getItems().so
        return true;
    }
    public Button getAddCourseButton(){

        return addCourseButton;
    }

    public Button getRemoveCourseButton(){

        return removeCourseButton;
    }
    public static String getSelectedCourse(){

        return listView.getSelectionModel().getSelectedItem();
    }


    private class CustomClassListCell extends ListCell<String>{

        private TextField textField;

        public CustomClassListCell(){

        }

        @Override
        public void startEdit(){

            super.startEdit();
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
            setGraphic(getGraphic());

        }

        @Override
        public void updateItem(String item, boolean empty){

            super.updateItem(item, empty);

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
                    setGraphic(null);
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

        private String getString(){
            return (getItem() == null ? "" : getItem().toString());
        }

    }

}
