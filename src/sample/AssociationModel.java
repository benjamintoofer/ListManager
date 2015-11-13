/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.ArrayList;
import java.util.Observable;
import java.io.Serializable;

/**
 *
 * @author Mans
 */
public class AssociationModel  extends Observable implements Serializable
{
    private Association addedAssoc = null;
    private ArrayList<Association> associationList;

    public AssociationModel()
     {
         init();       
     }

    
    public void init()
    {
        associationList = new ArrayList<Association>();

    }
    public boolean addAssociation(Association s)
    {
        boolean result = true;

        for(Association a: associationList){

            if((a.getSubjectObj().equals(s.getSubjectObj())) && (a.getClassObj().equals(s.getClassObj()))){
                System.out.println("Same assoc");
                result =  false;
            }
        }

        if(result){
            associationList.add(s);
            addedAssoc = s;
        }

        setChanged();
        notifyObservers("add");

        return result;
    }
    /*public Association getAssociation()
    {
        
    }*/
    public boolean removeAssociation(Subject s)
    {
        boolean result = true;
        ArrayList<Association> listToRemove = new ArrayList<Association>();

        for(Association a : associationList){
            if(a.getSubjectObj().equals(s)){

                listToRemove.add(a);
            }
        }

        if(listToRemove.isEmpty()){
            result = false;
        }else {
            for (Association a : listToRemove) {
                associationList.remove(a);
            }
        }

        return result;
    }

    public boolean removeAssociation(Subject s,Class c){

        boolean result = true;
        ArrayList<Association> listToRemove = new ArrayList<Association>();

        for(Association a : associationList){

            if(a.getSubjectObj().equals(s) && a.getClassObj().equals(c)){

                System.out.println("Removing Assoc: "+a.getSubjectObj().getName()+" "+a.getClassObj().getClassName());
                listToRemove.add(a);
            }
        }

        if(listToRemove.isEmpty()){

            result = false;

        }else {

            for (Association a : listToRemove) {

                associationList.remove(a);
            }
        }
        return result;
    }

    public boolean removeAssociation(Class c){

        boolean result = true;
        ArrayList<Association> listToRemove = new ArrayList<Association>();

        for(Association a : associationList){
            if( a.getClassObj().equals(c)){

                listToRemove.add(a);
            }
        }

        if(listToRemove.isEmpty()){

            result = false;
        }else {
            for (Association a : listToRemove) {
                associationList.remove(a);
            }
        }

        return result;
    }

    public ArrayList<Association> queryByClass(Class className){

        ArrayList<Association> newList = new ArrayList<Association>();

        if(associationList == null){
            associationList = new ArrayList<Association>();
        }

        for(Association a: associationList){

            if(a.getClassObj().equals(className)){
                newList.add(a);
            }
        }
        return newList;
    }

    public ArrayList<Association> queryBySubject(Subject subject){

        ArrayList<Association> newList = new ArrayList<Association>();

        if(associationList == null){
            associationList = new ArrayList<Association>();
        }

        for(Association a: associationList){

            if(a.getSubjectObj().equals(subject)){

                newList.add(a);
            }
        }

        return newList;
    }

    public Association getAddedAssociation(){

        return  addedAssoc;
    }
    public String printAssociations(){

        StringBuilder returnString = new StringBuilder();

        for(Association a: associationList){

            returnString.append("Class: "+a.getClassObj().getClassName()+" Subject: "+a.getSubjectObj().getName()+"\n");
        }

        return returnString.toString();
    }
    
}
