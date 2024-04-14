package controller;


import building.Building;



public class ElevatorControllerImpl {
  private Building building;

  /**
   * Constructor for the ElevatorControllerImpl class
   */
  public ElevatorControllerImpl(int numFloors, int numElevators, int capacity) {
    building = new Building(numFloors, numElevators, capacity);
  }

}
