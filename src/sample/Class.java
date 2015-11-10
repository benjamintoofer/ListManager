package sample;

import java.io.Serializable;
import java.util.ArrayList;

/*
    Class Attributes:

    - className = name of the class
    - classDescription = description of the class

 */
public class Class implements Serializable{
	public String className = "";
	public String classDescription = "";

    public Class(String name, String desc){
        className = name;
        classDescription = desc;
    }

    public Class(){
        new Class("","");
    }

    public boolean equals(Class c, String s){

        return c.getClassName().equals(s);
    }
	//Getters and Setters
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassDescription() {
		return classDescription;
	}
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	

}