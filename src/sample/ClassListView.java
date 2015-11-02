package sample;

/**
 * Created by benjamintoofer on 11/1/15.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ClassListView extends VBox{

    private  ListView<String> listView;
    private MenuBar courseMenuBar;

    public ClassListView(){

        init();
    }

    private void init(){

        //Instantiate listView
        listView = new ListView<>();
        listView.setPrefSize(300,900);
        ObservableList<String> tempItems = FXCollections.observableArrayList("COMP2730", "COMP2400", "COMP3351", "COMP3381");
        listView.setItems(tempItems);
        listView.setCellFactory((ListView<String> l)->new CustomClassListCell());


        //Instantiate courseMenuBar
        courseMenuBar = new MenuBar();
        Menu addCourseButton = new Menu("+");
        Menu removeCourseButton = new Menu("-");
        courseMenuBar.getMenus().addAll(addCourseButton,removeCourseButton);


        this.getChildren().addAll(listView,courseMenuBar);
    }

    private class CustomClassListCell extends ListCell<String>{

        private TextField textField;

        public CustomClassListCell(){

        }

        @Override
        public void startEdit(){
            System.out.println("Start edit 1");
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
            setGraphic(getGraphic());

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
                    setGraphic(getGraphic());
                }
            }
            super.updateListView(this.getListView());
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
