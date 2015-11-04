package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class ClassListModel extends Observable{

    private ArrayList<Class> classList;

    public ClassListModel(){
        init();
    }

    private void init(){

        classList = new ArrayList<>();
    }
    public void addClassToList(Class newClass){

        if(classList == null)
            classList = new ArrayList<Class>();

        classList.add(newClass);
        Collections.sort(classList,new Comparator<Class>() {
            @Override
            public int compare(Class o1, Class o2)
            {
                return o1.getClassName().compareToIgnoreCase(o2.getClassName());
            }
        });

        setChanged();
        notifyObservers("add");
    }
    public boolean modifyClass(String oldName, String newName, String newDesc){


        for(Class c : classList){
            if(c.getClassName().equals(oldName)){

                int index = classList.indexOf(c);
                classList.get(index).setClassName(newName);
                classList.get(index).setClassDescription(newDesc);
                Collections.sort(classList,new Comparator<Class>() {
                    @Override
                    public int compare(Class o1, Class o2)
                    {
                        return o1.getClassName().compareToIgnoreCase(o2.getClassName());
                    }
                });

                setChanged();
                notifyObservers("add");
                return true;
            }
        }
        return false;
    }

    public String getDescByClass(String className){

        for(Class c : classList){
            if(c.getClassName().equals(className)){

                int index = classList.indexOf(c);
                return classList.get(index).getClassDescription();

            }
        }

        return null;
    }
    public boolean removeClassFromList(String removeClass){

        for(Class c : classList){
            if(c.getClassName().equals(removeClass)){

                int index = classList.indexOf(c);
                classList.remove(index);
                setChanged();
                notifyObservers("remove");
                return true;
            }
        }

        return false;
    }

    public ArrayList<Class> getClassList(){

        return classList;
    }
}
