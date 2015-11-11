package sample;


import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Observable;
import java.io.Serializable;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeModel extends Observable implements Serializable{

    private Subject rootNode;
    private Subject childToAdd = null;
    private Subject childToRemove = null;
    private Subject childToModify = null;
    private Tree<Subject> tree;

    public SubjectTreeModel(){

        init();
    }

    //Initialize
    private void init(){

        rootNode = new Subject("Root","Root Desc");
        tree = new Tree<>(rootNode);
    }

    public Subject getRootNode(){

        return rootNode;
    }

    public void findNode(Subject s){

        //return tree.findNode(s);
    }
    public void setSubjectAssociated(Subject subject, boolean value){

        Tree.Node<Subject> foundNode = tree.findNode(subject);

        int numAss = foundNode.getData().getNumberOfAssociationsMade();

        if(!value){

            if(numAss > 0)
                foundNode.getData().setNumberOfAssociationsMade(numAss - 1);

        }else{
            foundNode.getData().setNumberOfAssociationsMade(numAss + 1);
        }

        if(foundNode.getData().getNumberOfAssociationsMade() == 0){
            foundNode.getData().setAssociated(false);
        }else{
            foundNode.getData().setAssociated(true);
        }

        int counter = 0;

        //Figure out how many children nodes have been associated
        for(Tree.Node<Subject> n : foundNode.getChildren()){
            if(n.getData().isAssociated()){
                counter++;
            }
        }

        foundNode.getData().setNumberOfChildrenAssociated(counter);


    }
    public void updateTreeColorAssociation(Subject s){

        System.out.print(s.getName()+" + ");
        tree.updateParentAssociations(s);
    }

    public ArrayList<Subject> getLeavesFromNode(Subject subject){

        return tree.getLeavesFromObject(subject);
    }
    public ArrayList<Subject> getChildrenFromNodePostOrder(Subject subject){

        return tree.getChildrenFromObjectPostOrder(subject);
    }

    public ArrayList<Subject> getChildrenFromNodeBFS(Subject subject){

        return tree.getChildrenFromObjectBFS(subject);
    }
    /*
        All editing methods of the Tree

        - addItem(parent,child) = finds the parent in te tree
        and adds the passed in child to the parent object

        - removeItem(child) = finds the passed in child and
        removes it from the tree including its children

        - modifyItem(obj, newName, newDesc) = find the Subject
        object that is passed in and set its new Name and Description
     */
    public void addItem(Subject parent, Subject child){

        childToAdd = child;
        tree.addElement(parent, child);
        tree.updateParentAssociations(child);

        //Notify observer: UI
        setChanged();
        notifyObservers("add");
    }
    public void removeItem(Subject child){

        tree.updateParentAssociations(child);
        tree.removeElement(child);
        childToRemove = child;

        //Notify observer: UI
        setChanged();
        notifyObservers("remove");
    }

    public void modifyItem(Subject obj,String newName, String newDesc){

        Tree.Node<Subject> modifiedNode = tree.findNode(obj);
        modifiedNode.getData().setName(newName);
        modifiedNode.getData().setDescription(newDesc);
        childToModify = obj;

        //Notify observer: UI
        setChanged();
        notifyObservers("modify");
    }
    ////////////////////////////////////////////////////////////
    /*
        Called in the update methods of the Observer(UI) in order to
        get the child objects are supposed to be removed, added, or
        modified from its view

     */
    public Subject getChildToAdd(){
        return childToAdd;
    }

    public Subject getChildToRemove(){
        return childToRemove;
    }

    public Subject getChildToModify(){
        return childToModify;
    }
////////////////////////////////////////////////////////////

    /*
        Print the current state of the tree
     */
    public String printTree(){
        return tree.printTree();
    }

    /*
        Get the description of the subject that is looked
        for from the model
     */
    public String getDesc(Subject obj){
        return tree.findNode(obj).getData().getDescription();
    }
}

