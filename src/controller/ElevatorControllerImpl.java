package controller;


import building.Building;



public class ElevatorControllerImpl {
  private Building building;

  /**
   * Constructor for the ElevatorControllerImpl class
   *
   * @param numFloors the number of floors in the building
   * @param numElevators the number of elevators in the building
   * @param capacity the capacity of each elevator
   */
  public ElevatorControllerImpl(int numFloors, int numElevators, int capacity) {
    building = new Building(numFloors, numElevators, capacity);
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
