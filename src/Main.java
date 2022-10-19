// Name: Isabella Livingston
// Date: 16 October 2022
// Description: This program will get 7 Unique letters from "words.txt" and display it to the user.
//              The user has to enter a word that consists of the same letters that are displayed
//              1 point for entering 4 letters, and more points for the length of the word

// Importing Assets
import java.io.*;
import java.util.*;

// Main Project
public class Main {
    // Main Function
    public static void main(String[] args) {
        // -------- DECLARING --------
        String END_PROGRAM = "bye";
        String command = "a";
        Integer score = 0;
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> recorded_words = new ArrayList<>();


        // Getting 7 Unique Letters
        ArrayList<Character> unique_letters = generate_string();

        // -------- OUTPUT --------
        displaying_output(unique_letters, score);

        // Ends the Program if the User types in "bye"
        while (!command.equals(END_PROGRAM)){

            // -------- INPUT --------
            command = getting_user_word(scanner, unique_letters, END_PROGRAM, recorded_words, score);

            // Checks to Make sure that the command isn't equal to "bye"
            if(!command.equals(END_PROGRAM)){

                // -------- PROCESSING --------
                score = updating_score(command, score);

                // Keeps track of words the User Entered
                recorded_words.add(command);

            }

        }

        // Goodbye Message
        System.out.println("Program Finished... Goodbye!");
    }

    // Updating Score Function
    public static Integer updating_score(String command, Integer score){

        // If the score is 4 letters, then the player gets 1 point
        if (command.length() == 4){
            score += 1;
        }

        // Anything more than 4, the player will get the length of the input for their points
        else{
            score += command.length();
        }

        // Prints Score
        System.out.println("Score: " + score);

        // Returns Score
        return score;
    }

    // Getting User Word Function
    public static String getting_user_word(Scanner scanner, ArrayList<Character> unique_letters, String END_PROGRAM, ArrayList<String> recorded_words, Integer score){

        // Gets User Input
        String new_user_word = scanner.nextLine();

        // Checks to see if the input is valid
        while(is_invalid(new_user_word, unique_letters,  END_PROGRAM, recorded_words, score)){

            // If it's not valid, then the user has to enter the input again
            new_user_word = scanner.nextLine();

        }

        // Returns User Input
        return new_user_word;

    }

    // Is Invalid Loop
    public static Boolean is_invalid(String new_user_word, ArrayList<Character> unique_letters, String END_PROGRAM, ArrayList<String> recorded_words, Integer score) {


        boolean has_same_word_from_file;

        // If the User Input is "bye" then returns as false and ends the program
        if (new_user_word.equals(END_PROGRAM)) {
            return false;
        }

        // Mix: Shuffles the Unique Letters around and displays the result
        //      Then the user has to enter the input again
        if (new_user_word.equals("mix")){

            Collections.shuffle(unique_letters);
            displaying_output(unique_letters, score);
            return true;

        }

        // ls: Lists Correct Words so far
        //     Then the user has to enter the input again
        if(new_user_word.equals("ls")){

            // If the User doesn't have any correct words, then it tells the user that
            if (recorded_words.isEmpty()){
                System.out.println("   Error: There Aren't Any Words You Found So Far");
                System.out.println("          Try Again When You Found A Word");
            }

            // Else it displays the correct words
            else{
                for (String recorded_word : recorded_words) {
                    System.out.println(recorded_word);
                }

            }

            // Prints Score
            System.out.println("Score: " + score);

            // User has to enter input again
            return true;

        }


        // Checks to see if the length of the input is less than 4 letters
        if (new_user_word.length() < 4) {

            // Returns Error Message
            System.out.println("   Error: Word Needs to Be More Than 4 Letters");

            // User has to re-enter input
            return true;
        }


         // Compares the user's word to the unique letters; Sees if there's at least one similar letter

         for (int user_letter = 0; user_letter < new_user_word.length(); user_letter++){

             // Resets Variable
             has_same_word_from_file = false;


             for (Character unique_letter : unique_letters) {

                 // If there's a similar letter then it keeps note of that
                 if (Character.toUpperCase(new_user_word.charAt(user_letter)) == Character.toUpperCase(unique_letter)) {
                     has_same_word_from_file = true;
                 }
             }

            // If there isn't a similar letter, it tells the user that
             if (!has_same_word_from_file){

                 System.out.println("   Error: Word doesn't have the same letters from the Unique Letters");
                 return true;
             }

         }


         // Checks to See if the Word is already recorded

        for (String recorded_word : recorded_words) {

            if (new_user_word.equals(recorded_word)) {
                System.out.println("   Error: This Word is Already Recorded");
                return true;
            }
        }


        // Everything Below This Comment (In the Function) Checks to see if the Inputted Word is in the File
        // Declares Buffered Reader
         BufferedReader reader;

         try {

             // Reads the words.txt file and puts line in variable
             reader = new BufferedReader(new FileReader("words.txt"));
             String line = reader.readLine();

             // Checks every line in file to see if it's similar
             while ((line != null)) {

                 // Going to Next Line
                 line = reader.readLine();

                 // Checks to see if the Line isn't null and user has the same word in the file
                 if ((line != null) && (line.equals(new_user_word))){

                     // If it can find a match, then the user doesn't have to enter an input again
                     return false;
                 }
             }

             // Closes the reader
             reader.close();
         }

         // Catch for Error
         catch (IOException e) {
             System.out.println("  Error: Couldn't Access File");
         }

         // If it can't find the same word, then the user has to enter the input again
         System.out.println("   Error: Couldn't Find Word In List");
         return true;


    }

    // Displaying Output Function
    public static void displaying_output(ArrayList<Character> unique_letters, Integer score){

        // Prints the Unique Letters and Score
        for (Character unique_letter : unique_letters) {
            System.out.printf("%9s", Character.toLowerCase(unique_letter));
        }

        System.out.println("\nScore: " + score);

    }

    // Generates String Function
    public static ArrayList<Character> generate_string(){

        // Declaring Variables
        //char[] unique_letters = {};
        ArrayList<Character> unique_letters = new ArrayList<>();
        File file = new File("words.txt");
        final RandomAccessFile reads_file;


        // Tries to Get a Random Word from File
        try {

            reads_file = new RandomAccessFile(file, "r");

            // Gets a Random Position in the File
            long randomLocation = (long) (Math.random() * reads_file.length());

            // Looks for Position
            reads_file.seek(randomLocation);

            // Reads It and Stores in Variable
            String randomLine = reads_file.readLine();

            // Checks to See if it's Unique:
            // - If It's Unique: Then the Word will be Mixed and Displayed
            // - Else: It Gets a New Random Line in File
            while(isUnique(randomLine)){

                // Same Things As Above
                randomLocation = (long) (Math.random() * reads_file.length());
                reads_file.seek(randomLocation);
                randomLine = reads_file.readLine();

            }

            // Adds Each Letter of the Unique Word to a List
            for (int word = 0; word < randomLine.length() ; word++){
                unique_letters.add(randomLine.charAt(word));
            }

            // Closes The File
            reads_file.close();
        }

        // Catch for Error
        catch (IOException e) {
            System.out.println("  Error: Couldn't Access File");
        }

        // Mixes List so that it doesn't print out the exact word of the unique letters
        Collections.shuffle(unique_letters);

        // Returns Unique Letters
        return unique_letters;

    }

    // Is Unique Function: Got Help With it From TA In Computer Science Lab
    public static Boolean isUnique(String random_word){

        // Declares Set
        Set<Character> new_set = new HashSet<>();

        // Check to see if the length of the word is 7 letters
        if (random_word.length() != 7){
            return true;
        }

        // Adds Each Letter to the Set
        for (int letter = 0; letter < random_word.length(); letter++){
            new_set.add(random_word.charAt(letter));
        }

        // Since Elements in the Set are not allowed to duplicate, it checks to see if any letters repeat
        return !(new_set.size() == random_word.length());
    }
}