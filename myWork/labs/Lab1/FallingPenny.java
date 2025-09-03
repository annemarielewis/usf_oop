
/**
 * Annemarie Lewis - Lab 1
 * CS 514 - Fall 2025
 * FallingPenny.java
 */

import java.math.*;
import java.lang.Math;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class FallingPenny {

    // Static declaration of variables makes the variables available to all static
    // methods within the FallingPennys class:
    static double acceleration = 9.8;
    static double terminalVelocity = 18.0;
    static double timeToTerminalVelocity = terminalVelocity / acceleration;
    static double accelDistance = 0.5 * acceleration * timeToTerminalVelocity * timeToTerminalVelocity;

    // ________________________________________________MAIN
    // METHOD______________________________________________
    public static void main(String[] args) { // main method!!! Calculating using height of 381 ft skyscraper!!!

        double ESBheight = 381 * 0.3048; // meters->converted 381 feet to meters, cause gets wrong answer without
        double fallingTime = Math.sqrt(2 * ESBheight / acceleration);
        System.out.println("It takes " + fallingTime + " seconds to reach the ground.");
        // It takes 4.868239425299542 seconds to reach the ground.

        double velocity = fallingTime * acceleration;
        System.out.println("The velocity before hitting the ground is " + velocity + " m/s");
        // The velocity before hitting the ground is 47.70874636793552 m/s,
        // when NOT accounting for air resistance, which creates terminal veliocity

        // accounting for terminal velocity:

        System.out.println(
                "There's " + timeToTerminalVelocity + " seconds before the penny reaches terminal velocity at 18m/s^2");
        // There's 1.8367346938775508 seconds before the penny reaches terminal velocity
        // at 18m/s^2

        System.out.println("and the penny travels " + accelDistance + " meters during this time.");
        // and the penny travels 16.53061224489796 meters during this time.

        // Part 5. And how long will the penny fall at terminal velocity?
        double distanceRemaining = ESBheight - accelDistance;
        double timeAtTerminalVelocity = distanceRemaining / terminalVelocity;
        System.out.println("the penny will fall for " + timeAtTerminalVelocity
                + " seconds longer once it hits terminal velocity.");
        // _________________________

        // answer for 100 meters using fallingTime method below:
        double timeFor100m = fallingTime(100);
        System.out.println("Time to fall 100 meters: " + timeFor100m + " seconds");

        // _________________________

        // answer for 100 meters on the moon using spaceFallingTime method, below:

        double timeFor100Moon = spaceFallingTime(100, "moon");
        System.out.println("Time to fall 100 meters on the moon: " + timeFor100Moon + " seconds");

        // _________________________

        // answer for height and planet location based on user input:

        Scanner scanner = new Scanner(System.in); // creating a Scanner object that allows your program to read input
                                                  // typed by the user from the keyboard.

        System.out.println("Welcome to the Space Falling Time Calculator!");
        System.out.println("Type 'exit' at any time to quit.\n");

        while (true) { // “Keep looping forever… until I manually break out of it with "exit"-->will
                       // keep the program going for new user input of different planets

            System.out.print("Enter the building height in meters: ");
            String heightInput = scanner.nextLine(); // .nextLine-->reads everything the user typed until they pressed
                                                     // Enter

            // exit logic
            if (heightInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            double height = Double.parseDouble(heightInput); // changing input of height from a string (automatically
                                                             // strored as string) to a number

            System.out.print("Enter the planet (Earth, Moon, or Mars, etc): ");
            String planet = scanner.nextLine();

            // exit logic
            if (planet.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            try {
                double time = spaceFallingTime(height, planet);
                System.out.printf("Time to fall " + height + " meters on " + planet + "is " + time + "\n\n"); // \n\n
                                                                                                              // has
                                                                                                              // console
                                                                                                              // skip a
                                                                                                              // line
                                                                                                              // for
                                                                                                              // next
                                                                                                              // message
                                                                                                              // to user
                                                                                                              // to be
                                                                                                              // printed
                                                                                                              // on a
                                                                                                              // separate
                                                                                                              // line:
                                                                                                              // looks
                                                                                                              // cleaner!
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }

        scanner.close();

    }
    // __________________________fallingTime Method: same as what's happening in
    // main but better and all in one functionality________________

    public static double fallingTime(double fallingDistance) {
        if (fallingDistance <= accelDistance) {
            // Terminal velocity is NOT reached
            return Math.sqrt((2 * fallingDistance) / acceleration);
        } else {
            // Terminal velocity IS reached
            double remainingDistance = fallingDistance - accelDistance;
            double timeAtTerminalVelocity = remainingDistance / terminalVelocity;
            return timeToTerminalVelocity + timeAtTerminalVelocity;
        }
    }

    // __________________________spaceFallingTime Method: methd that tales in
    // distance and acceleration of terminal velocity on other
    // planets________________

    public static double spaceFallingTime(double distance, String planet) {
        double acceleration = getAcceleration(planet); // using the helper function/method below
        double terminalVelocity = 18.0; // m/s //assuming it's the same-->though it's probably not
        double timeToTerminalVelocity = terminalVelocity / acceleration;
        double accelDistance = 0.5 * acceleration * timeToTerminalVelocity * timeToTerminalVelocity;

        if (distance <= accelDistance) {
            return Math.sqrt((2 * distance) / acceleration);
        } else {
            double remainingDistance = distance - accelDistance;
            double timeAtTerminalVelocity = remainingDistance / terminalVelocity;
            return timeToTerminalVelocity + timeAtTerminalVelocity;
        }
    }

    // __________________________helper function/method for spaceFallingTime method,
    // above ^___________

    public static double getAcceleration(String planet) {
        switch (planet.toLowerCase()) {
            case "earth":
                return 9.8;
            case "moon":
                return 1.6;
            case "mars":
                return 3.69;
            case "jupiter":
                return 24.79;
            case "saturn":
                return 10.44;
            case "titan":
                return 1.4;
            case "venus":
                return 8.87;
            default:
                System.out.println("Error, Unknown location: " + planet);
                return -1;
        }
    }

}