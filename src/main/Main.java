package main;

import controller.ElevatorControllerImpl;
import view.ElevatorSystemView;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    // Default values in case arguments are not provided or are invalid
    int numFloors = 10;
    int numElevators = 2;
    int capacity = 3;

    // Check if the right number of arguments are passed
    if (args.length >= 3) {
      try {
        numFloors = Integer.parseInt(args[0]);
        numElevators = Integer.parseInt(args[1]);
        capacity = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
        System.err.println("Invalid arguments. Please ensure all arguments are integers.");
        System.exit(1);  // Exit the program with an error code
      }
    } else {
      System.out.println("Not enough arguments provided. Using default values:");
      System.out.println("Number of Floors: " + numFloors);
      System.out.println("Number of Elevators: " + numElevators);
      System.out.println("Capacity per Elevator: " + capacity);
    }

    // Create the controller with the building parameters
    ElevatorControllerImpl controller = new ElevatorControllerImpl(numFloors, numElevators, capacity);

    // Start the view and pass the controller to it
    SwingUtilities.invokeLater(() -> new ElevatorSystemView(controller));
  }
}