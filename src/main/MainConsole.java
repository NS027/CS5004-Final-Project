package main;

import building.Building;
import building.BuildingReport;
import elevator.ElevatorReport;
import java.util.Scanner;
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

    Building building = new Building(numFloors, numElevators, numPeople);
//    String[] introText = {
//        "Welcome to the Elevator System!",
//        "This system will simulate the operation of an elevator system.",
//        "The system will be initialized with the following parameters:",
//        "Number of floors: " + numFloors,
//        "Number of elevators: " + numElevators,
//        "Number of people: " + numPeople,
//        "The system will then be run and the results will be displayed.",
//        "",
//        "Press enter to continue."
//    };
//    System.out.println(String.join("\n", introText));

    Scanner scanner = new Scanner(System.in);
    boolean flag = true;

    System.out.println("Elevator System is Initialized.");
    System.out.println("Enter 'start' to begin, " +
        "'stop' to end, " +
        "'step' to make movement, " +
        "'request' to add request, " +
        "'status' to get status, " +
        "'quit' to exit.");

    while (flag) {
      System.out.println(">>> ");
      String command = scanner.nextLine();
      String[] para = command.split(" ");

      switch (para[0].toLowerCase()) {
        case "start":
          building.startElevatorSystem();
          printHelper1(building);
          break;
        case "stop":
          building.stopElevatorSystem();
          printHelper2(building);
          flag = false;
          break;
        case "step":
          building.stepElevatorSystem();
          printHelper2(building);
          break;
        case "request":
          if (para.length == 3) {
            int startFloor = Integer.parseInt(para[1]);
            int endFloor = Integer.parseInt(para[2]);
            building.addRequestToElevatorSystem(new Request(startFloor, endFloor));
            printHelper2(building);
          } else {
            System.out.println("Invalid request format. Use 'request [startFloor] [endFloor]'.");
          }
          break;
        case "quit":
          System.exit(0);
          break;
        default:
          System.out.println("Invalid Command. " +
              "Available commands: start, stop, request, step, quit.");
          break;
      }
    }
  }
  private static void printHelper1(Building building) {
    BuildingReport report = building.getStatusElevatorSystem();
    System.out.println("Building Status:");
    System.out.println("Number of Floors: " + report.getNumFloors());
    System.out.println("Number of Elevators: " + report.getNumElevators());
    System.out.println("Elevator Capacity: " + report.getElevatorCapacity());
  }
  private static void printHelper2(Building building) {
    BuildingReport report = building.getStatusElevatorSystem();
    System.out.println("\nBuilding Status:" + report.getSystemStatus());
    for (ElevatorReport elevatorReport : report.getElevatorReports()) {
      System.out.println("\nElevator ID: " + elevatorReport.getElevatorId());
      System.out.println("Current Floor: " + elevatorReport.getCurrentFloor());
      System.out.println("Direction: " + elevatorReport.getDirection());
      System.out.println("Door Status: " + (elevatorReport.isDoorClosed() ? "Closed" : "Open"));
      System.out.println("Is Taking Requests: " + elevatorReport.isTakingRequests());
    }
  }
}