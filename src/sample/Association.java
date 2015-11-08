package sample;

public class Association {

	public Subject subjectObj;
	public Class classObj;
	
	
	public Association(Class classObj, Subject subjectObj){

        this.classObj = classObj;
        this.subjectObj = subjectObj;
	}
	
    public Subject getSubjectObj(){

        return subjectObj;
    }

    public void setSubjectObj(Subject obj){

        subjectObj = obj;
    }

    public Class getClassObj(){

        return classObj;
    }
    public void setClassObj(Class obj){

        classObj = obj;
    }
	
	
}
