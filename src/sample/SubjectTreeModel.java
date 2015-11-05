package sample;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeModel extends Observable{

    private Subject rootNode;
    private Subject childToAdd = null;
    private Subject childToRemove = null;
    private Subject childToModify = null;
    Tree<Subject> tree;

    public SubjectTreeModel(){

        init();
    }

    private void init(){

        rootNode = new Subject("Root","Root Desc");
        tree = new Tree<>(rootNode);
        System.out.println(((Subject)tree.getRootElement().getData()).getPath());
        System.out.println(tree.printTree());
    }

    public void addItem(Subject parent, Subject child){
        childToAdd = child;
        System.out.println("Parent: "+parent.getName());
        tree.addElement(parent, child);

        setChanged();
        notifyObservers("add");
    }
    public void removeItem(Subject child){
        tree.removeElement(child);
        childToRemove = child;
        setChanged();
        notifyObservers("remove");
    }

    public void modifySubject(Subject obj,String newName, String newDesc){

        Tree.Node<Subject> modifiedNode = tree.findNode(obj);
        modifiedNode.getData().setName(newName);
        modifiedNode.getData().setDescription(newDesc);
        childToModify = obj;

        setChanged();
        notifyObservers("modify");
    }

    public Subject getChildToAdd(){
        return childToAdd;
    }

    public Subject getChildToRemove(){
        return childToRemove;
    }

    public Subject getChildToModify(){
        return childToModify;
    }


    public String printTree(){
        return tree.printTree();
    }

    public String getDesc(Subject obj){
        return tree.findNode(obj).getData().getDescription();
    }
}

