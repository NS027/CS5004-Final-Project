package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import scanerzus.Request;


/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {

  /**
   * The constructor for the building.
   *
   * @param numberOfFloors the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity the capacity of the elevators in the building.
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity) {
    System.out.println("Building constructor called");
    System.out.println("numberOfFloors: " + numberOfFloors);
    System.out.println("numberOfElevators: " + numberOfElevators);
    System.out.println("elevatorCapacity: " + elevatorCapacity);

    System.out.println("\n\nYet to be built.");


  }
}


