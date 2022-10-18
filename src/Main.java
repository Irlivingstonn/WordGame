// Name: Isabella Livingston
// Date: 16 October 2022
// Description:

// Importing Assets
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Main Project
public class Main {
    // Main Function
    public static void main(String[] args) {
        // -------- DECLARING --------
        String END_PROGRAM = "bye";
        String command = "a";
        File words_file = new File("words.txt");
        Random random = new Random();
        Integer score = 0;
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> recorded_words = new ArrayList<>();


        // Getting 7 Unique Letters
        ArrayList<Character> unique_letters = generate_string(random);

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


        Boolean has_same_word_from_file = false;

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
                for(Integer word = 0; word < recorded_words.size(); word++){
                    System.out.println(recorded_words.get(word));
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

         for (Integer user_letter = 0; user_letter < new_user_word.length(); user_letter++){

             // Resets Variable
             has_same_word_from_file = false;


            for (Integer unique_word = 0; unique_word < unique_letters.size(); unique_word++){

                // If there's a similar letter then it keeps note of that
                if (Character.toUpperCase(new_user_word.charAt(user_letter)) == unique_letters.get(unique_word)){
                    has_same_word_from_file = true;
                }
            }

            // If there isn't a similar letter, it tells the user that
             if (!has_same_word_from_file){

                 System.out.println("   Error: Word doesn't have the same letters from the Unique Letters");
                 return true;
             }

         }

         // Declares Buffered Reader
         BufferedReader reader;

         try {

             // Reads the words.txt file and puts line in variable
             reader = new BufferedReader(new FileReader("words.txt"));
             String line = reader.readLine();

             // Checks every line in file to see if it's similar
             while ((line != null) && (!has_same_word_from_file)) {

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
             e.printStackTrace();
         }

         // If it can't find the same word, then the user has to enter the input again
         System.out.println("   Error: Couldn't Find Word In List");
         return true;


    }

    // Displaying Output Function
    public static void displaying_output(ArrayList<Character> unique_letters, Integer score){

        // Prints the Unique Letters and Score
        for(Integer i = 0; i < unique_letters.size(); i++){
            System.out.printf("%9s", Character.toLowerCase(unique_letters.get(i)));
        }

        System.out.println("\nScore: " + score);

    }

    // Generates String Function
    public static ArrayList<Character> generate_string(Random random){

        // Declaring Variables
        Integer MAX = 7;
        //char[] unique_letters = {};
        ArrayList<Character> unique_letters = new ArrayList<>();



        // Declares Buffered Reader
        BufferedReader reader;

        try {

            // Reads the words.txt file and puts line in variable
            reader = new BufferedReader(new FileReader("words.txt"));
            String random_line = reader.readLine();



            // Closes the reader
            reader.close();
        }

        // Catch for Error
        catch (IOException e) {
            e.printStackTrace();
        }

        /////////////////////////////////////////


        // Getting 3 random numbers
        for (Integer element = 0; element < MAX; element++) {

            // Gets a random number
            char random_letter = (char) (random.nextInt(26) + 65);


            // Gets a new random number if it's repeated
            // Doesn't stop until there's a different value
            while(unique_letters.contains(random_letter)){
                random_letter = (char) (random.nextInt(26) + 65);
            }

            // Adds random number to arraylist
            unique_letters.add(random_letter);

        }

        // Returns Unique Letters
        return unique_letters;

    }

}