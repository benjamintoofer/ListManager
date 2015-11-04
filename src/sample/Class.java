package sample;

import java.util.ArrayList;


public class Class {
	public String className = "";
	public String classDescription = "";
	public int CRN;
	ArrayList<Subject> subjects = new ArrayList<Subject>();
//	ArrayList<LearningOutcome> associatedLO = new ArrayList<LearningOutcome>();

    public Class(String name, String desc){
        className = name;
        classDescription = desc;
    }

    public Class(){
        new Class("","");
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
	public int getCRN() {
		return CRN;
	}
	public void setCRN(int crn) {
		CRN = crn;
	}
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}
	public void setAssociatedCS(ArrayList<Subject> associatedCT) {
		this.subjects = subjects;
	}
/*	public ArrayList<LearningOutcome> getAssociatedLO() {
		return associatedLO;
	}
	public void setAssociatedLO(ArrayList<LearningOutcome> associatedLO) {
		this.associatedLO = associatedLO;
	}*/
	
	public void addSubject(Subject newSubject){
		subjects.add(newSubject);
	}
	public void removeSubject(int subjectID){
		for(int i = 0; i< subjects.size(); i++){
			subjects.remove(subjectID);
		}
		
	}
/*	public void addLearningOutcome(LearningOutcome outcome){
		associatedLO.add(outcome);
	}
	public void removeLearningOutcome(int outcomeID){
		for(int i=0; i< associatedLO.size(); i++){
			associatedLO.remove(outcomeID);
		} 
		
	}*/
	
	

}