package sample;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class SubjectTreeModel extends Observable{

    Subject rootNode;

    public SubjectTreeModel(){

        init();
    }

    private void init(){

        rootNode = new Subject("Root","Root Desc");
    }
}
