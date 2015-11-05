package sample;


import java.util.Observable;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeModel extends Observable{

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

        //Notify observer: UI
        setChanged();
        notifyObservers("add");
    }
    public void removeItem(Subject child){

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

