package sample;

import java.util.ArrayList;

/**
 * Created by benjamintoofer on 11/1/15.
 */
public class Subject {

    private String name;
    private String description;
    private int position;
    private boolean isAssociated;
    private ArrayList<Subject> subTopicList;
    private ArrayList<String> learningOutcomeList;


    public Subject(){

    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<Subject> getSubTopicList()
    {
        return subTopicList;
    }

    public void addSubTopic(Subject addSubject)
    {
        this.subTopicList.add(addSubject);
    }

    public ArrayList<String> getLearningOutcomeList()
    {
        return learningOutcomeList;
    }

    public void addLearningOutcome(String learnOut){

        this.learningOutcomeList.add(learnOut);

    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}
