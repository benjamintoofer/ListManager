package sample;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeModel extends Observable{

    Subject rootNode;
    Subject childToAdd = null;
    Subject childToRemove = null;
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
        tree.addElement(parent,child);

        setChanged();
        notifyObservers("add");
    }
    public void removeItem(Subject child){
        tree.removeElement(child);
        childToRemove = child;
        setChanged();
        notifyObservers("remove");
    }
    public Subject getChildToAdd(){
        return childToAdd;
    }

    public Subject getChildToRemove(){
        return childToRemove;
    }

    public String printTree(){
        return tree.printTree();
    }
}
