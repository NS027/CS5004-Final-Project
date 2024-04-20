package controller;


import building.Building;
import building.BuildingReport;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import scanerzus.Request;


public class ElevatorControllerImpl {
  private Building building;
  private Random random;
  private List<Request> activeRequests = new ArrayList<>();


  /**
   * Constructor for the ElevatorControllerImpl class
   *
   * @param numFloors the number of floors in the building
   * @param numElevators the number of elevators in the building
   * @param capacity the capacity of each elevator
   */
  public ElevatorControllerImpl(int numFloors, int numElevators, int capacity) {
    building = new Building(numFloors, numElevators, capacity);
    random = new Random();
  }

  /**
   * This is method used to start the elevator controller
   */
  public void startSystem() {
    building.startElevatorSystem();
  }

  /**
   * This is method used to stop the elevator controller
   */
  public void stopSystem() {
    building.stopElevatorSystem();
  }

  /**
   * This is method used to shut down the elevator system.
   */
  public void shutDownSystem() {
    // Step 1: Stop the elevator system
    building.stopElevatorSystem();

    // Step 2: Loop until all elevators reach the ground floor
    boolean allElevatorsGrounded = false;
    while (!allElevatorsGrounded) {
      allElevatorsGrounded = true;
      for (int elevatorID = 0; elevatorID < building.getNumberOfElevators(); elevatorID++) {
        ElevatorReport report = building.getElevatorReport(elevatorID);
        if (report.getCurrentFloor() != 0) {
          allElevatorsGrounded = false;
        }
      }
      // Perform a system step if not all elevators are grounded
      if (!allElevatorsGrounded) {
        building.stepElevatorSystem();
      }
    }

    // Step 3: Additional step to mark elevators as out of service
    // This step ensures that all elevators are set to out of service at the ground floor
    building.stepElevatorSystem();

    System.out.println("All elevators are now grounded and out of service.");
  }

  /**
   * This is method used to step the elevator controller
   */
  public void stepSystem() {
    building.stepElevatorSystem();
  }

  /**
   * This is method used to process n step to the elevator controller
   */
  public void stepSystemN(int n) {
    for (int i = 0; i < n; i++) {
      building.stepElevatorSystem();
    }
  }

  /**
   * This is method used to add request to the elevator controller
   */
  public void addRequest(int fromFloor, int toFloor) {
    Request newRequest = new Request(fromFloor, toFloor);
    activeRequests.add(newRequest);
    building.addRequestToElevatorSystem(new Request(fromFloor, toFloor));
  }

  public List<Request> getActiveRequests() {
    return activeRequests;
  }

  /**
   * This is method used to add n request to the elevator controller
   */
  public void addRandomRequest(int n) {
    for (int i = 0; i < n; i++) {
      boolean isUp = Math.random() > 0.5; // Randomly decide the direction
      generateRequest(isUp);
    }
  }


  /**
   * This is method used to generate random request
   */
  private void generateRequest(boolean isUp) {
    int startFloor = random.nextInt(building.getNumberOfFloors());
    int endFloor;
    do {
      endFloor = random.nextInt(building.getNumberOfFloors());
    } while (endFloor == startFloor); // Ensure end floor is not the same as start floor

    if (isUp && startFloor > endFloor) {
      // Swap floors for up direction if start is above end
      int temp = startFloor;
      startFloor = endFloor;
      endFloor = temp;
    } else if (!isUp && startFloor < endFloor) {
      // Swap floors for down direction if start is below end
      int temp = startFloor;
      startFloor = endFloor;
      endFloor = temp;
    }

    // building.addRequestToElevatorSystem(new Request(startFloor, endFloor));
    Request newRequest = new Request(startFloor, endFloor);
    activeRequests.add(newRequest); // Add to the active requests list
    building.addRequestToElevatorSystem(newRequest); // Also add to the building system
  }


  /**
   * This is method used to quit the elevator controller
   */
  public void quitSystem() {
    System.exit(0);
  }

  /**
   * This is method used to get the building
   */
  public Building getBuildingStatus() {
    return building;
  }

  /**
   * This is method used to get building report
   */
  public BuildingReport getBuildingReport() {
    return building.getStatusElevatorSystem();
  }

  /**
   * This is method used to get elevator report
   */
  public ElevatorReport getElevatorReport(int elevatorId) {
    return building.getElevatorReport(elevatorId);
  }
}
