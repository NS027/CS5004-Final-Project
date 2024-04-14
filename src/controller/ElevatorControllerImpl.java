package controller;


import building.Building;
import java.util.Random;
import scanerzus.Request;


public class ElevatorControllerImpl {
  private Building building;
  private Random random;

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
    building.addRequestToElevatorSystem(new Request(fromFloor, toFloor));
  }

  /**
   * This is method used to add n request to the elevator controller
   */
  public void addRandomRequest(int n) {
    // for evenly distributed requests
    int half = n / 2;

    // for the up requests
    for (int i = 0; i < half; i++) {
      generateRequest(true);
    }
    // for the down requests
    for (int i = 0; i < n - half; i++) {
      generateRequest(false);
    }
  }

  /**
   * This is method used to generate random request
   */
  private void generateRequest(boolean isUp) {
    int startFloor, endFloor;
    if (isUp) {
      startFloor = random.nextInt(building.getNumberOfFloors() - 1);
      endFloor = random.nextInt(building.getNumberOfFloors() - startFloor - 1) + startFloor + 1;
    } else {
      endFloor = random.nextInt(building.getNumberOfFloors() - 1);
      startFloor = random.nextInt(building.getNumberOfFloors() - endFloor - 1) + endFloor + 1;
    }
    building.addRequestToElevatorSystem(new Request(startFloor, endFloor));
  }

  /**
   * This is method used to quit the elevator controller
   */
  public void quitSystem() {
    System.exit(0);
  }

}
