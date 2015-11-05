package sample;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeModel extends Observable{

    Subject rootNode;
    Subject childToAdd = null;
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
        tree.addElement(parent,child);

        setChanged();
        notifyObservers("add");
    }

    public Subject getChildToAdd(){
        return childToAdd;
    }

    public String printTree(){
        return tree.printTree();
    }
}
