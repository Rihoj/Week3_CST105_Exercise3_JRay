package week3programmingexercise3;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author james
 */
public class GuessingGame {

    //Create Scanner Object
    private static Scanner input;
    //Set minimum to ask for
    private static final int MIN = 1;
    //Set maximum to ask for
    private static final int MAX = 10000;
    //Set the minimum the user is currently being asked for.
    private static int userMin;
    //Set the maximum the user is currently being asked for.
    private static int userMax;
    //Number of times the user has guessed.
    private static int userGuessCount = 0;
    //The random number the program is looking for.
    private static int number;
    //Allow only so many error before closing application
    private static final int MAX_ERRORS = 5;
    //Current error count
    private static int errorCount = 0;

    /**
     * Sets the number to guess and the input scanner.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        number = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
        input = new Scanner(System.in);
        askUserForGuess(MIN, MAX);
    }

    //Asks the user to guess a number.
    private static void askUserForGuess(int minimum, int maximum) {
        System.out.print("Enter a guess between " + minimum + " and " + maximum + " : ");
        userMin = minimum;
        userMax = maximum;
        //Fill userGuess with integer from user input and performs check.
        try {
            int userGuess = input.nextInt();
            if (validate(userGuess)) {
                userGuessCount++;
                performCheck(userGuess);
            } else {
                System.err.println("The value submited is not within the correct range.");
                askUserForGuess(userMin, userMax);
            }
        } catch (InputMismatchException e) {
            input.next();
            error();
        }
    }

    //validate that the string is within the available range.
    private static boolean validate(int userGuess) {
        if (userGuess >= userMin && userGuess <= userMax) {
            return true;
        }
        return false;
    }

    //Checks to see if the user's input matches the system's number
    private static void performCheck(int userGuess) {
        if (userGuess == number) {
            System.out.println("You WIN. It took you " + userGuessCount + " guesses.");
            System.exit(0);
        } else if (userGuess > number) {
            System.out.println("LOWER");
            userMax = userGuess - 1;
            askUserForGuess(userMin, userMax);
        } else if (userGuess < number) {
            System.out.println("HIGHER");
            userMin = userGuess + 1;
            askUserForGuess(userMin, userMax);
        }
    }

    //Count and output error. If max errors is reached close application to avoid looping.
    private static void error() {
        errorCount++;
        System.err.println("The value submitted is not a valid integer.");
        if (errorCount < MAX_ERRORS) {
            askUserForGuess(userMin, userMax);
        } else {
            System.err.println("Too many errors have occured. The program is now closing.");
            System.exit(0);
        }
    }
}
