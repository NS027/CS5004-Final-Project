package main;

import building.Building;
import building.BuildingReport;
import elevator.ElevatorReport;
import java.util.Random;
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

    System.out.println("Elevator System is Initialized.");
    System.out.println("Enter 'start' to begin, "
        + "'stop' to stop, "
        + "'step' to make movement, "
        + "step n to make n movements, "
        + "'request' to add request, "
        + "request n to add n random requests,"
        + "'status' to get status, "
        + "'quit' to exit.");

    while (true) {
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
          break;
        case "step":
          if (para.length == 2) {
            int n = Integer.parseInt(para[1]);
            for (int i = 0; i < n; i++) {
              building.stepElevatorSystem();
            }
          } else {
            building.stepElevatorSystem();
          }
          stringOutput2(building);
          break;
        case "request":
          if (para.length > 1) {
            int requests = Integer.parseInt(para[1]);
            generateRandomRequests(building, requests, numFloors);
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

  private static void generateRandomRequests(Building building, int n, int floors) {
    Random rand = new Random();
    for (int i = 0; i < n; i++) {
      int start = rand.nextInt(floors);
      int end;
      do {
        end = rand.nextInt(floors);
      } while (start == end);
      building.addRequestToElevatorSystem(new Request(start, end));
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