package sample;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by benjamintoofer on 11/1/15.
 */

/*
    Subject Attributes:

    - name = Name of the subject
    - description = Description of the subject
    - position = Position of the subject relative to its parent subject
    - path = Path of the subject relative to the root node
 */

public class Subject implements Serializable{

    private String name;
    private String description;
    private int position;
    private String path;
    private boolean associated;
    private boolean leaf;
    private int numberOfChildrenAssociated;
    private int numberOfAssociationsMade;
    private int numberOfChildren;
    private transient Color currentColor;


    public Subject(String name,String desc){
        this.name = name;
        this.description = desc;
        this.associated = false;
        this.leaf = true;
        this.currentColor = Color.RED;
        this.numberOfChildrenAssociated = 0;
        this.numberOfAssociationsMade = 0;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public boolean isAssociated()
    {
        return associated;
    }

    public void setAssociated(boolean associated)
    {
        this.associated = associated;
    }

    public boolean isLeaf()
    {
        return leaf;
    }

    public void setLeaf(boolean leaf)
    {
        this.leaf = leaf;
    }

    public int getNumberOfChildrenAssociated()
    {
        return numberOfChildrenAssociated;
    }

    public void setNumberOfChildrenAssociated(int numberOfChildrenAssociated)
    {
        this.numberOfChildrenAssociated = numberOfChildrenAssociated;

        if(leaf){
            currentColor = associated ? Color.GREEN : Color.RED;
        }else{
            currentColor = (numberOfChildrenAssociated == 0) ? Color.RED : ((numberOfChildrenAssociated == numberOfChildren) ? Color.GREEN : Color.ORANGE);
        }

    }

    public int getNumberOfChildren()
    {
        return numberOfChildren;
    }
    public void decrementNumberOfChildrenAssociated(){

        this.setNumberOfChildrenAssociated(numberOfChildrenAssociated - 1);
    }

    public void incrementNumberOfChildrenAssociated()
    {
        this.setNumberOfChildrenAssociated(numberOfChildrenAssociated + 1);
    }
    public void decrementNumberOfChildren(){

        this.numberOfChildren--;
    }

    public void incrementNumberOfChildren()
    {
        this.numberOfChildren++;
    }

    public Color getCurrentColor()
    {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor)
    {
        this.currentColor = currentColor;
    }

    public int getNumberOfAssociationsMade()
    {
        return numberOfAssociationsMade;
    }

    public void setNumberOfAssociationsMade(int numberOfAssociationsMade)
    {
        this.numberOfAssociationsMade = numberOfAssociationsMade;
    }
}
