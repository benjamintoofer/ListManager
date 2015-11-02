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

    private  ListView<String> listView;
    private HBox buttonBox;

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
        Button addCourseButton = new Button("+");
        addCourseButton.setId("add_course_button");
        addCourseButton.setOnAction(new AddRemoveClassHandler());
        Button removeCourseButton = new Button("-");
        removeCourseButton.setId("remove_course_button");
        removeCourseButton.setOnAction(new AddRemoveClassHandler());
        buttonBox.getChildren().addAll(addCourseButton,removeCourseButton);




        this.getChildren().addAll(listView,buttonBox);
    }

    private boolean removeCourse(){
        return true;
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




    private class AddRemoveClassHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent e){

            System.out.println("Handling");
            if(e.getTarget().getClass().toString().equals("class javafx.scene.control.Button")){

                if(((Button)e.getTarget()).getId().equals("add_course_button")){
                    System.out.println("Adding course");
                }
                if(((Button)e.getTarget()).getId().equals("remove_course_button")){
                    System.out.println("Removing course");
                }
            }
        }

    }
}
