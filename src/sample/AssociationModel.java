/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Mans
 */
public class AssociationModel  extends Observable
{
    public AssociationModel()
     {
         init();       
     }
    private ArrayList<Association> associationList;
    
    public void init()
    {
        associationList = new ArrayList<Association>();
    }
    public void addAssociation(Association s)
    {
        associationList.add(s);
        setChanged();
        notifyObservers();
    }
    /*public Association getAssociation()
    {
        
    }*/
    public void removeAssociation(Association s)
    {
        
    }
    
}
