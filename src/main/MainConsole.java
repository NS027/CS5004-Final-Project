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

    final int numFloors = 10;
    final int numElevators = 2;
    final int numPeople = 3;

    Building building = new Building(numFloors, numElevators, numPeople);

    Scanner scanner = new Scanner(System.in);
    boolean flag = true;

    System.out.println("Elevator System is Initialized.");
    System.out.println("Enter 'start' to begin, "
        + "'stop' to stop, "
        + "'step' to make movement, "
        + "'request' to add request, "
        + "'status' to get status, "
        + "'quit' to exit.");

    while (flag) {
      System.out.println(">>> ");
      String command = scanner.nextLine();
      String[] para = command.split(" ");

      switch (para[0].toLowerCase()) {
        case "start":
          building.startElevatorSystem();
          System.out.println("Elevator System is initiated.");
          stringOutput1(building);
          break;
        case "stop":
          building.stopElevatorSystem();
          stringOutput2(building);
          //flag = false;
          break;
        case "step":
          building.stepElevatorSystem();
          stringOutput2(building);
          break;
        case "request":
          if (para.length == 3) {
            int startFloor = Integer.parseInt(para[1]);
            int endFloor = Integer.parseInt(para[2]);
            building.addRequestToElevatorSystem(new Request(startFloor, endFloor));
            stringOutput2(building);
          } else {
            System.out.println("Invalid request format. Use 'request [startFloor] [endFloor]'.");
          }
          break;
        case "quit":
          System.exit(0);
          break;
        case "status":
          stringOutput2(building);
          break;
        default:
          System.out.println("Invalid Command. "
              + "Available commands: start, stop, request, step, quit.");
          break;
      }
    }
  }

  private static void stringOutput1(Building building) {
    BuildingReport report = building.getStatusElevatorSystem();
    System.out.println("====================================");
    System.out.println("Building Status:");
    System.out.println("------------------------------------");
    System.out.println("Number of Floors: " + report.getNumFloors());
    System.out.println("Number of Elevators: " + report.getNumElevators());
    System.out.println("Elevator Capacity: " + report.getElevatorCapacity());
    System.out.println("====================================");
  }

  private static void stringOutput2(Building building) {
    BuildingReport report = building.getStatusElevatorSystem();
    System.out.println("Building Status:" + report.getSystemStatus());
    for (ElevatorReport elevatorReport : report.getElevatorReports()) {
      System.out.println("------------------------------------");
      System.out.println("Elevator ID: " + elevatorReport.getElevatorId());
      System.out.println("Current Floor: " + elevatorReport.getCurrentFloor());
      System.out.println("Elevator moving direction: " + elevatorReport.getDirection());
      System.out.println("Door Status: " + (elevatorReport.isDoorClosed() ? "Closed" : "Open"));
      System.out.println("Accepting request: " + elevatorReport.isTakingRequests());
      System.out.println("------------------------------------");
    }
  }
}