package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Andrew on 11/4/15.
 */
public class IOUtil {
// TODO: remove static main upon full impl, change parser to static
    public static void main(String args[]) {
        try {
            //parse("//Users//Andrew//Desktop//CS2013-final-report.txt");
            importTxt("//Users//Andrew//Desktop//testin.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parse(String filepath) throws IOException {
        File f = new File(filepath);
        FileInputStream input = new FileInputStream(f);
        Scanner scanner = new Scanner(input);
        String line = "";
        char[] chars;

        String subjDesc = "";
        String innerLine ="";
        String requirement = "";
        int tab = 0;
        boolean topics = false;
        boolean makeAssoc = false;
        boolean foundSubj = false;
        boolean crossRef = false;
        ArrayList<String> knowledgeType = new ArrayList<String>();
        //TODO: Placeholder Objects (begin with set to null)
        int count = 0;

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
                        parseProcess(requirement);
                        requirement = "";
                        crossRef = false;
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

        parseProcess(requirement);

        System.out.println("Done parsing");
    }

    private static void parseProcess(String requirement) {
        Scanner scanner = new Scanner(requirement);
        String[] splitSubject;
        String temp = "";
        String previous = "previous";
        String lines = "";
        String subject = scanner.nextLine();
        //System.out.println("Found Learning Requirement Subject: " + subject.trim());
        splitSubject = subject.split("/");
        System.out.println("SUBJ: " + splitSubject[1]);
        System.out.println("GENRE: " + splitSubject[0]);
        boolean endOfSection = false;

        while(scanner.hasNextLine()) {
            temp = scanner.nextLine();
            if ((!temp.contains("Topics:"))) {
                lines += temp + " ";
            } else {
                //System.out.println("Processing Subject Desc: " + lines.replaceAll(" +", " "));
                System.out.println("DESC: " + lines.replaceAll(" +", " "));
                processSubjectDesc(lines.replaceAll(" +", " ").replaceAll("\f", "").trim());
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
                processTopics(lines.replaceAll(" +", " "));
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
        processLearningOutcomes(lines.replaceAll(" +", " "));
        temp = "";
        //System.out.println("----------END----------\n");
        System.out.println("\n");

    }

    private static void processTopics(String topics) {

    }

    private static void processTopicDesc(String topicDesc) {

    }

    private static void processLearningOutcomes(String learningOutcome) {

    }

    private static void processLearningOutcomeDesc(String learningOutcomeDesc) {

    }

    private static void processSubjectDesc(String subjectDesc) {

    }

    public static void importTxt(String filepath) throws IOException {
        File f = new File(filepath);
        FileInputStream input = new FileInputStream(f);
        String line = "";
        String requirement = "";
        boolean foundSubj = false;
        boolean crossRef = false;
        ArrayList<String> knowledgeType = new ArrayList<String>();
        //TODO: Placeholder Objects (begin with set to null)
        int chunkNo = 0;

        BufferedReader br = new BufferedReader(new FileReader(filepath));
        while((line=br.readLine())!=null)
        {
            chunkNo++;
            if (line.isEmpty() || line.equals("\f")) {
                continue;
            } else {
                if (line.matches("[A-Z]+/{1}([A-z0-9]\\s?)+[^ \\.]{1,}")) {
                    if (foundSubj) {
                        //System.out.println("PROCESSING CHUNK: " + requirement);
                        process(requirement, chunkNo);
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
        process(requirement, chunkNo);

        System.out.println("\nDone parsing");
    }

    private static void process(String requirement, int chunkNo) {
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
        temp = scanner.nextLine();
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
                }
                //hasParent = false;
            }
        }
    }

    private static void exportTxt(String fileName, ArrayList<Subject> subjects) {

    }
}