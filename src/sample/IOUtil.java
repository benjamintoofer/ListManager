package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Andrew on 11/4/15.
 *
 * ---------------------------This is an experimental add-on feature that is not yet completed (not required)
 *
 */
public class IOUtil {

    private static String previousGenre = "";

// TODO: remove static main upon full impl, change parser to static
    public static void main(String args[]) {
        try {
            parseToTxt("//Users//Andrew//Desktop//CS2013-final-report.txt");
            //importTxt("//Users//Andrew//Desktop//text.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Experimental feature to parse a PDF converted to TXT. Contains numerous algorithms to attempt to parse through and identify target values.
    public static void parseToTxt(String filepath) throws IOException {
        String[] ext = filepath.split("\\.");
        String outFileName = ext[0] + "-PARSED.txt";
        BufferedWriter output = new BufferedWriter(new FileWriter(outFileName));
        File f = new File(filepath);
        String line = "";
        String requirement = "";
        boolean foundSubj = false;
        ArrayList<String> knowledgeType = new ArrayList<String>();
        int count = 0;

        output.write("Classes:\n\n Subjects:\n\n\tName: Root\n\tDescription: Root Desc\n\tAssociation:\n\n");

        BufferedReader br = new BufferedReader(new FileReader(filepath));
        while((line=br.readLine())!=null)
        {
            line.replaceAll(":", ";");
            count++;
            if (line.isEmpty() || line.equals("\f") || line.matches("[- 0-9 -]+")) {
                continue;
            } else {
                if (line.matches("[A-Z]+/{1}([A-z0-9]\\s?)+[^ \\.]{1,}")) {
                    if (foundSubj) {
                        System.out.println("\n\nPROCESSING CHUNK: " + requirement);
                        parseProcess(requirement, output);
                        requirement = "";
                    }
                    foundSubj = true;
                    //System.out.println("----------BEGIN----------");
                    //System.out.println("FOUND: " + line + " AT: " + count);
                    System.out.println("LINE: " + count);
                    requirement += line + "\n";

                } else if (foundSubj) {
                    requirement += line + "\n";
                }
            }
        }

        parseProcess(requirement, output);

        System.out.println("Done parsing");

        output.flush();
        output.close();
    }

    // Parse() helper method, processes "chunks" (partitions of data) generated by the parse() method
    private static void parseProcess(String requirement, BufferedWriter output) throws IOException {
        Scanner scanner = new Scanner(requirement);
        String[] splitSubject;
        String temp = "";
        String lines = "";
        String subject = scanner.nextLine();
        splitSubject = subject.split("/");
        System.out.println("SUBJ: " + splitSubject[1]);
        System.out.println("GENRE: " + splitSubject[0]);

        if(splitSubject[0].equals(previousGenre)) {
        } else {
            output.write("\t\tName: " + splitSubject[0] + "\n\t\tDescription:\n\t\tAssociation:\n\n");
            previousGenre = splitSubject[0];
        }

        while(scanner.hasNextLine()) {
            temp = scanner.nextLine();
            if ((!temp.contains("Topics:"))) {
                lines += temp + " ";
            } else {
                //System.out.println("Processing Subject Desc: " + lines.replaceAll(" +", " "));
                System.out.println("DESC: " + lines.replaceAll(" +", " "));
                processSubjectDesc(lines.replaceAll(" +", " ").replaceAll("\f", "").trim(), splitSubject[1], output);
                break;
            }
        }
        lines = "";
        while(scanner.hasNextLine()) {
            temp = scanner.nextLine();
            if (!temp.contains("Learning Outcomes:")) {
                lines += temp + " ";
            } else {
                //System.out.println("Processing Topics: " + lines.replaceAll(" +", " ").replaceAll("\f", ""));
                System.out.println("TPCS: " + lines.replaceAll(" +", " "));
                output.write("\t\t\t\tName: Topics\n\t\t\t\tDescription: \n\t\t\t\tAssociation:\n\n");
                processTopics(lines.replaceAll(" +", " "), output);
                break;
            }
        }
        lines = "";
        while(scanner.hasNextLine()) {
            temp = scanner.nextLine();
            if (temp.contains("         ") || temp.contains("    ") || temp.matches("\\[[\\w-]*\\]")) {
                lines += temp + " ";
            } else if (!(temp.startsWith(" ") || temp.startsWith("  ") || temp.startsWith("   ") || temp.startsWith("    ") || temp.startsWith("     "))){
                break;
            }
        }
        //System.out.println("Processing Learning Outcomes: " + lines.replaceAll(" +", " ").replaceAll("\f", ""));
        System.out.println("LO: " + lines.replaceAll(" +", " "));
        output.write("\t\t\t\tName: Learning Outcomes\n\t\t\t\tDescription: \n\t\t\t\tAssociation:\n\n");
        processLearningOutcomes(lines.replaceAll(" +", " "), output);
        temp = "";
        //System.out.println("----------END----------\n");
        System.out.println("\n");

    }

    //TODO: implement these helper methods to clean up the experimental parser
    private static void processTopics(String topics, BufferedWriter output) throws IOException {
        String topic = "";
        String topicDesc = "";
        String tpcsPrefix = "";
        String[] outcomes = topics.split("\\uFFFD");

        for (int i = 0; i < outcomes.length; i++) {
            int endIndex = outcomes[i].length();
            if(endIndex > 50) {
                endIndex = 50;
            }
            topic = outcomes[i].substring(0, endIndex) + "...";
            topicDesc = outcomes[i];
            output.write("\t\t\t\t\tName: " + topic + "\n\t\t\t\t\tDescription: " + topicDesc + "\n\t\t\t\t\tAssociation:\n\n");
        }

    }

    private static void processLearningOutcomes(String learningOutcome, BufferedWriter output) throws IOException {
        String outcome = "";
        String outcomeDesc = "";
        String[] outcomes = learningOutcome.split("[0-9]{1,2}\\.");

        for (int i = 0; i < outcomes.length; i++) {
            int endIndex = outcomes[i].length();
            if(endIndex > 50) {
                endIndex = 50;
            }
            outcome = outcomes[i].substring(0, endIndex) + "...";
            outcomeDesc = outcomes[i];
            output.write("\t\t\t\t\tName: " + outcome + "\n\t\t\t\t\tDescription: " + outcomeDesc + "\n\t\t\t\t\tAssociation:\n\n");
        }
    }

    private static void processSubjectDesc(String subjectDesc, String subject, BufferedWriter output) throws IOException {
        output.write("\t\t\tName: " + subject + "\n\t\t\tDescription: " + subjectDesc + "\n\t\t\tAssociation:\n\n");
    }

    // Imports from a txt file by identifying target "chunks" and sending them to the "process" helper method
    public static void importTxt(String filepath) throws IOException {
        File f = new File(filepath);
        FileInputStream input = new FileInputStream(f);
        String line = "";
        String requirement = "";
        boolean foundSubj = false;
        boolean crossRef = false;
        String[] classInfo;
        ArrayList<String> knowledgeType = new ArrayList<String>();
        ArrayList<Class> classes = new ArrayList<Class>();
        //TODO: Placeholder Objects (begin with set to null)
        int chunkNo = 0;

        BufferedReader br = new BufferedReader(new FileReader(filepath));
        while((line=br.readLine())!=null)
        {
            chunkNo++;
            if (line.equals("Classes:")) {
                while((line=br.readLine())!=null && !line.isEmpty()) {
                    if (line.contains(":")) {
                        classInfo = line.split(":");
                        Class classObj = new Class(classInfo[0].trim(), classInfo[1].trim());
                        classes.add(classObj);
                    } else {
                        Class classObj = new Class(line.trim(), "");
                        classes.add(classObj);
                    }
                    System.out.println("Class: " + line);
                }
            }
            if (line.isEmpty() || line.equals("\f")) {
                continue;
            } else {
                if (line.matches("[A-Z]+/{1}([A-z0-9]\\s?)+[^ \\.]{1,}")) {
                    if (foundSubj) {
                        //System.out.println("PROCESSING CHUNK: " + requirement);
                        process(requirement, chunkNo, classes);
                        requirement = "";
                        crossRef = false;
                    }
                    foundSubj = true;
                    System.out.println("\nLine: " + chunkNo);
                    requirement += line + "\n";
                } else if (foundSubj) {
                    requirement += line + "\n";
                }
            }
        }
        //System.out.println("PROCESSING CHUNK: " + requirement);
        process(requirement, chunkNo, classes);

        System.out.println("\nDone parsing");
    }

    // This helper method parses chunks generated by the importTxt method. It identifies target values and adds them to the model object.
    private static ArrayList<Subject> process(String requirement, int chunkNo, ArrayList<Class> classes) {
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        Subject parentCategory;
        Subject parentSubject;
        String temp = "";
        String line = "";
        String desc = "";
        int lineNo = chunkNo;
        boolean hasParent = false;
        boolean topics = false;
        boolean outcomes = false;
        String[] split;
        String[] assoc;
        Scanner scanner = new Scanner(requirement);
        split = scanner.nextLine().split("/");
        System.out.println("Category: " + split[0]);
        System.out.println("Subject: " + split[1]);

        parentCategory = new Subject(split[0], "");
        subjects.add(parentCategory);

        if (scanner.hasNextLine()) {
            temp = scanner.nextLine();
        } else {
            System.out.println("Empty subject.");
            return null;
        }

            while(!temp.contains("\t") && scanner.hasNextLine()) {
                desc += temp;
                //System.out.println(desc);
                temp = scanner.nextLine();
                lineNo++;
            }

            System.out.println("Description: " + desc);


            while((temp.contains("\t\t") || temp.contains("\t\t\t") || temp.contains("\t")) && scanner.hasNextLine()) {
                if (temp.equals("\tTopics")) {
                    topics = true;
                } else if (temp.equals("\tLearning Outcomes")) {
                    topics = false;
                    outcomes = true;
                } else if (topics == false && outcomes == false) {
                    System.out.println("Possible syntax error: Cannot parse topics or outcomes near line: " + lineNo);
                }
                if (topics) {
                    if (temp.contains("\t\t") && !hasParent) {
                        hasParent = true;
                        System.out.println("Topic: " + temp.replaceAll("\\t",""));
                        if (temp.contains(":")) {
                            split = temp.split(":");
                            //ADD TOPIC split[0]

                            //ADD ASSOC split[1]

                            assoc = split[1].split(",");
                            for (int i = 0; i < assoc.length; i++) {
                                //Create assoc
                                System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                            }
                        }
                    } else
                    if (temp.contains("\t\t\t") && hasParent) {
                        System.out.println("Desc: " + temp.replaceAll("\\t",""));
                        hasParent = false;
                    } else if (temp.contains("\t\t\t")) {
                        System.out.println("This description does not have a parent node! Check your syntax near line " + lineNo);
                    } else if (!temp.contains("Topics")) {
                        System.out.println("lTopic: " + temp.replaceAll("\\t",""));
                        if (temp.contains(":")) {
                            split = temp.split(":");
                            //ADD TOPIC split[0]

                            //ADD ASSOC split[1]
                            assoc = split[1].split(",");
                            for (int i = 0; i < assoc.length; i++) {
                                //Create assoc
                                System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                            }
                        }
                        //hasParent = false;
                    }
                } else if (outcomes) {
                    if (temp.contains("\t\t") && !hasParent) {
                        hasParent = true;
                        System.out.println("Outcome: " + temp.replaceAll("\\t",""));
                        if (temp.contains(":")) {
                            split = temp.split(":");
                            //ADD TOPIC split[0]

                            //ADD ASSOC split[1]
                            assoc = split[1].split(",");
                            for (int i = 0; i < assoc.length; i++) {
                                //Create assoc
                                System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                            }
                        }
                    } else
                    if (temp.contains("\t\t\t") && hasParent) {
                        System.out.println("Desc: " + temp.replaceAll("\\t",""));
                        hasParent = false;
                    } else if (temp.contains("\t\t\t")) {
                        System.out.println("This description does not have a parent node! Check your syntax near line " + lineNo);
                    } else if (!temp.contains("Learning Outcomes")) {
                        System.out.println("lOutcome: " + temp.replaceAll("\\t", ""));
                        if (temp.contains(":")) {
                            split = temp.split(":");
                            //ADD TOPIC split[0]

                            //ADD ASSOC split[1]
                            assoc = split[1].split(",");
                            for (int i = 0; i < assoc.length; i++) {
                                //Create assoc
                                System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                            }
                        }
                        //hasParent = false;
                    }
                }
                lineNo++;
                temp = scanner.nextLine();
            }

        //Final iter
        if (temp.equals("\tTopics")) {
            topics = true;
        } else if (temp.equals("\tLearning Outcomes")) {
            topics = false;
            outcomes = true;
        } else if (topics == false && outcomes == false) {
            System.out.println("Possible syntax error: Cannot parse topics or outcomes near line: " + lineNo);
        }
        if (topics) {
            if (temp.contains("\t\t") && !hasParent) {
                hasParent = true;
                System.out.println("Topic: " + temp.replaceAll("\\t",""));
                if (temp.contains(":")) {
                    split = temp.split(":");
                    //ADD TOPIC split[0]

                    //ADD ASSOC split[1]
                    assoc = split[1].split(",");
                    for (int i = 0; i < assoc.length; i++) {
                        //Create assoc
                        System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                    }
                }
            } else
            if (temp.contains("\t\t\t") && hasParent) {
                System.out.println("Desc: " + temp.replaceAll("\\t",""));
                hasParent = false;
            } else if (temp.contains("\t\t\t")) {
                System.out.println("This description does not have a parent node! Check your syntax near line " + lineNo);
            } else if (!temp.contains("Topics")) {
                System.out.println("lTopic: " + temp.replaceAll("\\t",""));
                if (temp.contains(":")) {
                    split = temp.split(":");
                    //ADD TOPIC split[0]

                    //ADD ASSOC split[1]
                    assoc = split[1].split(",");
                    for (int i = 0; i < assoc.length; i++) {
                        //Create assoc
                        System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                    }
                }
                //hasParent = false;
            }
        } else if (outcomes) {
            if (temp.contains("\t\t") && !hasParent) {
                hasParent = true;
                System.out.println("Outcome: " + temp.replaceAll("\\t",""));
                if (temp.contains(":")) {
                    split = temp.split(":");
                    //ADD TOPIC split[0]

                    //ADD ASSOC split[1]
                    assoc = split[1].split(",");
                    for (int i = 0; i < assoc.length; i++) {
                        //Create assoc
                        System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                    }
                }
            } else
            if (temp.contains("\t\t\t") && hasParent) {
                System.out.println("Desc: " + temp.replaceAll("\\t",""));
                hasParent = false;
            } else if (temp.contains("\t\t\t")) {
                System.out.println("This description does not have a parent node! Check your syntax near line " + lineNo);
            } else if (!temp.contains("Learning Outcomes")) {
                System.out.println("lOutcome: " + temp.replaceAll("\\t", ""));
                if (temp.contains(":")) {
                    split = temp.split(":");
                    //ADD TOPIC split[0]

                    //ADD ASSOC split[1]
                    assoc = split[1].split(",");
                    for (int i = 0; i < assoc.length; i++) {
                        //Create assoc
                        System.out.println("Association: " + split[0].replaceAll("\\t","") + " Course: " + assoc[i]);

                    }
                } else {
                    //ADD TOPIC (NO ASSOCIATIONS)
                }
                //hasParent = false;
            }
        }
        return subjects;
    }

    // Add-on method (placeholder for now) for the experimental parser
    private static void importClasses(ArrayList<Class> classes) {
        //ADD CLASS LIST
    }

    // This function will traverse the association tree(s) created in the model object and print them in a readable fashion
    public static void exportTxt(File file, RunListManager man) throws IOException {
        File f = file;
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        /*
            Print classes
         */
        writer.write("Classes:\n");

        //Get list of class and loop through each class and print their name and description
        ArrayList<Class> classList = man.getClassListModel().getClassList();

        for(Class c : classList){

            writer.write(c.getClassName()+" : "+c.getClassDescription()+"\n");
        }

        /*
            Print Subjects
         */
        writer.write("\n");

        Subject rootSubject = man.getSubjectTreeModel().getRootNode();
        ArrayList<Subject> subjectList = man.getSubjectTreeModel().getChildrenFromNodePreOrder(rootSubject);
        String path;


        for(Subject s : subjectList){

            //Subject currentSubject = queue.poll();
            ArrayList<Association> assocList = man.getAssociationModel().queryBySubject(s);
            //numChildren = s.getNumberOfChildren();

            String indents = "";
            path = s.getPath();
            int counter = 0;

            //Find the depth of the subject in the subject's path
            for(int i = 0; i < path.length();i++){
                if(path.charAt(i) == '.')
                    counter++;

            }

            if(counter == 2){

                writer.write(s.getName()+"/");

            }else if(counter == 3){
                writer.write(s.getName()+"\n"+s.getDescription()+"\n");
            }else if(counter > 3){

                int tabs = counter -  3;

                for(int i = 0; i < tabs; i++){

                    indents = indents + "\t";
                }

                if(!assocList.isEmpty()){

                    writer.write(indents+s.getName()+" : ");
                    StringBuilder assocString = new StringBuilder();

                    if(!assocList.isEmpty()) {

                        for (Association a : assocList) {

                            assocString.append(a.getClassObj().getClassName() + ", ");

                        }

                        int lastIndex = assocString.lastIndexOf(",");
                        assocString.replace(lastIndex, lastIndex + 1, "\n");
                        writer.write(assocString.toString() + indents + "\t" + s.getDescription()+"\n");

                    }
                }else{

                    writer.write(indents+s.getName()+"\n"+indents+"\t"+s.getDescription()+"\n");
                }

            }

        }
        writer.flush();
        writer.close();
    }

    public static void exportTxt2(File file, RunListManager man) throws IOException {
        File f = file;
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        /*
            Print classes
         */
        writer.write("Classes:\n");

        //Get list of class and loop through each class and print their name and description
        ArrayList<Class> classList = man.getClassListModel().getClassList();

        for(Class c : classList){

            writer.write("Name: "+c.getClassName()+"\nDescription: "+c.getClassDescription()+"\n\n");
        }

        /*
            Print Subjects
         */
        writer.write("\n Subjects:\n\n");

        Subject rootSubject = man.getSubjectTreeModel().getRootNode();
        ArrayList<Subject> subjectList = man.getSubjectTreeModel().getChildrenFromNodePreOrder(rootSubject);
        String path;


        for(Subject s : subjectList){

            ArrayList<Association> assocList = man.getAssociationModel().queryBySubject(s);


            path = s.getPath();
            String indent = "";
            //Find the depth of the subject in the subject's path
            for(int i = 0; i < path.length();i++){

                if(path.charAt(i) == '.'){

                    indent = indent + "\t";
                }

            }

            writer.write(indent+"Name: "+s.getName()+"\n"+indent+"Description: "+s.getDescription()+"\n"+indent+"Association: ");

            StringBuilder assocStr = new StringBuilder();

            if(s.isAssociated()){

                for(Association a: assocList){

                    assocStr.append(a.getClassObj().getClassName()+", ");
                }
                int lastIndex = assocStr.lastIndexOf(",");
                assocStr.replace(lastIndex, lastIndex, "\n\n");
                writer.write(assocStr.toString());
            }else{
                writer.write("\n\n");
            }


        }
        writer.flush();
        writer.close();
    }

    public static void importText2(File file,RunListManager rlm) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        boolean firstSubject = true;
        String str = "";
        ArrayList<Class> classList = new ArrayList<Class>();
        ClassListModel classListModel = new ClassListModel();
        SubjectTreeModel subjectTreeModel = new SubjectTreeModel();
        AssociationModel associationModel = new AssociationModel();
        Tree<Subject> tree = null;


        boolean createClass = false;

        while((line=br.readLine())!=null){


            //Read in the Classes
            if(line.contains("Classes:")){
                System.out.println("Found CLASSES ");
                createClass = true;
            }
            if(line.contains("Subjects:")){


                System.out.println("Found SUBJECTS ");
                createClass = false;
            }

            if(createClass){


                if(line.contains("Name:")){
                    str = str + line + "::";
                }

                if(line.contains("Description:")){
                    str = str + line;
                    classList.add(processClassChunk(str));
                    str = "";
                }
            }else{

                if(line.contains("Name:")){

                    //System.out.println("Subject: "+line+" has "+numChildren+" children");
                    str = str + line + "::";


                }else if(line.contains("Description:")) {

                    str = str + line + "::";
                }else if(line.contains("Association:")){

                    int tabCounter = 0;
                    for(int i = 0; i < line.length(); i++){
                        if(line.charAt(i) == '\t'){
                            tabCounter++;
                        }
                    }

                    str = str + line;
                    Subject subjectToAdd = processSubjectChunk(str);

                    if(firstSubject){

                        tree = new Tree<Subject>(subjectToAdd);
                        firstSubject = false;

                    }else{

                        if(tree != null){
                            tree.addNodeToLevel(tabCounter,subjectToAdd);
                            tree.updateParentAssociations(subjectToAdd);
                        }

                    }

                    /*
                        Process Associations
                     */
                    int index = line.indexOf(":");
                    String tempStr = line.substring(index+1,line.length());
                    tempStr = tempStr.trim();

                    if(!tempStr.isEmpty()){

                        String[] assocArr = tempStr.split(",");

                        for(String s : assocArr){
                            s = s.trim();

                            for(Class c : classList){

                                if(c.getClassName().equals(s))
                                    associationModel.addAssociation(new Association(c,subjectToAdd));
                            }

                        }
                    }


                    str = "";
                }

            }


        }



        subjectTreeModel.setTree(tree);
        classListModel.addClasses(classList);
        rlm.setClassListModel(classListModel);
        rlm.setSubjectTreeModel(subjectTreeModel);
        rlm.setAssociationModel(associationModel);
    }

    private static Class processClassChunk(String str)
    {

        Class c = new Class();
        String[] classString = str.split("::");
        for(String s : classString){

            if(s.contains("Name:")){

                int index = s.indexOf(":");
                String className = s.substring(index+1,s.length());
                className = className.trim();
                c.setClassName(className);
            }

            if(s.contains("Description:")){
                int index = s.indexOf(":");
                String classDesc = s.substring(index+1,s.length());
                classDesc = classDesc.trim();
                c.setClassDescription(classDesc);
            }
        }

        return c;

    }

    private static Subject processSubjectChunk(String str){

        Subject subject = new Subject("","");
        String[] subjectString = str.split("::");
        for(String s : subjectString){

            if(s.contains("Name:")){

                int index = s.indexOf(":");
                String subjectName = s.substring(index+1,s.length());
                subjectName = subjectName.trim();
                subject.setName(subjectName);
            }

            if(s.contains("Description:")){
                int index = s.indexOf(":");
                String subjectDesc = s.substring(index+1,s.length());
                subjectDesc = subjectDesc.trim();
                subject.setDescription(subjectDesc);
            }

            if(s.contains("Association:")){
                int index = s.indexOf(":");
                String subjectAssoc = s.substring(index+1,s.length());
                subjectAssoc = subjectAssoc.trim();

                if(subjectAssoc.isEmpty()){
                    subject.setAssociated(false);
                }else{
                    subject.setAssociated(true);
                    subject.setLeaf(true);

                    //Go through list of Assoc and count how many to set number of assoc
                    String[] assocString = s.split(",");
                    subject.setNumberOfAssociationsMade(assocString.length);
                }
            }
        }
        return subject;
    }

}
