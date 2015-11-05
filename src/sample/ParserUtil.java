package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Andrew on 11/4/15.
 */
public class ParserUtil {
// TODO: remove static main upon full impl, change parser to static
    public static void main(String args[]) {
        try {
            parse("//Users//Andrew//Desktop//testin.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void parse(String filepath) throws FileNotFoundException {
        File f = new File(filepath);
        FileInputStream input = new FileInputStream(f);
        Scanner scanner = new Scanner(input);
        String line = "";
        char[] chars;
        int tab = 0;
        boolean topics = false;
        boolean makeAssoc = false;
        //TODO: Placeholder Objects (begin with set to null)

        while(scanner.hasNext()) {
            line = scanner.nextLine();
            chars = line.toCharArray();
            for(int i = 0; i < chars.length; i++) {
                if (chars[i] == '\t') {
                    tab++;
                }
            }

            if (line.contains("Topics")) {
                topics = true;
            } else if (line.contains("Learning Outcomes")){topics = false;}

            if(tab == 0 && !line.trim().equals("") && !makeAssoc) {
                //Set subject to a new subject object
                System.out.println("SUBJECT: " + line.trim());
            } else if (tab == 0 && makeAssoc) {
                System.out.println("MAKING ASSOC");
                makeAssoc = false;
                //make the previous associations, and then set subject to a new subject object
                //if statements to check if topic or outcome is null, if not, add association
                //then set the placeholders back to null (so we can see if they exist in the outline (i.e., if a Learning Outcome exists for that subject
            } else if (tab == 2 && topics) {
                System.out.println("TOPIC: " + line.trim());
                makeAssoc = true;
                //Set topic to a new topic object

            } else if (tab == 2 && !topics) {
                System.out.println("OUTCOME: " + line.trim());
                makeAssoc = true;
                //Set learning outcome to a new LO object

            } else if (tab >= 3 && topics) {
                System.out.println("TOPIC DESC: " + line.trim());
                //Add desc to topic obj

            } else if (tab >= 3 & !topics) {
                System.out.println("LO DESC: " + line.trim());
                //Add desc to outcome obj

            }

            tab = 0;
        }
        System.out.println("Done parsing");
    }

}
