package main;

import java.util.Scanner;
import building.Building;
import scanerzus.Request;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * <p>
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  /**
   * The main method for the elevator system.
   * This method creates the elevator system and runs it.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // the number of floors, the number of elevators, and the number of people.

    final int numFloors = 11;
    final int numElevators = 8;
    final int numPeople = 3;


    String[] introText = {
        "Welcome to the Elevator System!",
        "This system will simulate the operation of an elevator system.",
        "The system will be initialized with the following parameters:",
        "Number of floors: " + numFloors,
        "Number of elevators: " + numElevators,
        "Number of people: " + numPeople,
        "The system will then be run and the results will be displayed.",
        "",
        "Press enter to continue."
    };

    for (String line : introText) {
      System.out.println(line);

    }
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();

    Building building = new Building(numFloors, numElevators, numPeople);

    System.out.println("Elevator System Is Initialized.");

    while (true) {
      System.out.println("Enter your command: (start/stop/step/request/status/quit): \n");
      String command = scanner.nextLine();

      switch(command) {
        case "start":
          building.startElevatorSystem();
          System.out.println("Elevator System Is Started.");
          break;
        case "stop":
          building.stopElevatorSystem();
          System.out.println("Elevator System Is Stopped.");
          break;
         case "step":
          building.stepElevatorSystem();
          System.out.println("Elevator System performed Stepped.");
          System.out.println(building.getStatusElevatorSystem().toString());
          break;
        case "request":
          System.out.println("Enter the from floor:");
          int fromFloor = scanner.nextInt();
          System.out.println("Enter the to floor:");
          int toFloor = scanner.nextInt();
          building.addRequestToElevatorSystem(new Request(fromFloor, toFloor));
          System.out.println("Request from floor " + fromFloor
              + " to floor " + toFloor + " is added.");
          break;
        case "status":
          System.out.println(building.getStatusElevatorSystem().toString());
          break;
        case "quit":
          System.exit(0);
          break;
        default:
          System.out.println("Invalid Command. Please try again.");
          break;
      }
    }




  }


}