package sample;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;

/**
 * Created by benjamintoofer on 11/3/15.
 */
public class DialogBox extends Dialog{

    Label classLabel = new Label("Name:");
    Label descLabel = new Label("Description:");

    TextField classTextField = new TextField();
    TextField descTextField = new TextField();

    ButtonType doneButton = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

    GridPane gridPane = new GridPane();


    public DialogBox(boolean withFields){
        super();

        if(withFields)
            initWithFields();
        else
            initWithOutFields();
    }

    private void initWithFields(){

        this.setTitle("Class Information");
        this.setHeaderText("Enter Class information");
        this.getDialogPane().getButtonTypes().addAll(doneButton,cancelButton);

        gridPane.add(classLabel,1,1);
        gridPane.add(classTextField,1,2);
        gridPane.add(descLabel,2,1);
        gridPane.add(descTextField,2,2);

        this.getDialogPane().setContent(gridPane);



    }

    private void initWithOutFields(){

        this.setTitle("Remove Class");
        this.setHeaderText("Are you sure you would like to remove class?");
        this.getDialogPane().getButtonTypes().addAll(yesButton,noButton);

    }

    public String getClassName(){
        return classTextField.getText();
    }

    public String getDesc(){
        return descTextField.getText();
    }

    public void setClassTextField(String name){
        classTextField.setText(name);
        classTextField.selectAll();
    }

    public void setDescTextField(String desc){
        descTextField.setText(desc);
    }

    public void setDialogContentText(String name){
        this.setContentText(name);
    }

}
