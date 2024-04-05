package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;

import java.util.ArrayList;
import java.util.List;


import scanerzus.Request;


/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {
  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;
  private ElevatorSystemStatus elevatorsStatus;
  private final List<Request> upRequests = new ArrayList<>();
  private final List<Request> downRequests = new ArrayList<>();
  private final ElevatorInterface[] elevators;

  /**
   * The constructor for the building.
   *
   * @param numberOfFloors the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity the capacity of the elevators in the building.
   * throws IllegalArgumentException if numberOfFloors is less than 2,
   *                                 numberOfElevators is less than 1,
   *                                 or elevatorCapacity is less than 1
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity) throws IllegalArgumentException{
    if (numberOfFloors < 2) {
      throw new IllegalArgumentException("numberOfFloors must be greater than or equal to 2");
    } else if (numberOfElevators < 1) {
      throw new IllegalArgumentException("numberOfElevators must be greater than or equal to 1");
    } else if (elevatorCapacity < 1) {
      throw new IllegalArgumentException("elevatorCapacity must be greater than or equal to 1");
    } else {
      this.numberOfFloors = numberOfFloors;
      this.numberOfElevators = numberOfElevators;
      this.elevatorCapacity = elevatorCapacity;
      this.elevators = new Elevator[numberOfElevators];

      for (int i = 0; i < numberOfElevators; ++i) {
        this.elevators[i] = new Elevator(numberOfFloors, this.elevatorCapacity);
      }

      this.elevatorsStatus = ElevatorSystemStatus.outOfService;

    }

    System.out.println("Building constructor called");
    System.out.println("numberOfFloors: " + numberOfFloors);
    System.out.println("numberOfElevators: " + numberOfElevators);
    System.out.println("elevatorCapacity: " + elevatorCapacity);

    System.out.println("\n\nYet to be built.");
  }

  /**
   * This method is used to start the elevator system.
   */
  @Override
  public void startElevatorSystem() {
    if (this.elevatorStatus != ElevatorSystemStatus.running) {
      if (this.elevatorStatus == ElevatorSystemStatus.stopping) {
        throw new IllegalStateException("Elevator cannot be started until it is stopped");
      } else {
        ElevatorInterface [] variable1 = this.elevators;
        int variable2 = variable1.length;

        for (int variable3 = 0; variable3 < variable2; ++variable3) {
          ElevatorInterface elevator = variable1[variable3];
          elevator.start();
        }
      }
    }
  }

}


