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
    if (this.elevatorsStatus != ElevatorSystemStatus.running) {
      if (this.elevatorsStatus == ElevatorSystemStatus.stopping) {
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

  /**
   * This method is used to stop the elevator system.
   */
  @Override
  public void stopElevatorSystem() {
    if (this.elevatorsStatus != ElevatorSystemStatus.outOfService
        && this.elevatorsStatus != ElevatorSystemStatus.stopping) {
      ElevatorInterface [] variable1 = this.elevators;
      int variable2 = variable1.length;

      for (int variable3 = 0; variable3 < variable2; ++variable3) {
        ElevatorInterface elevator = variable1[variable3];
        elevator.takeOutOfService();
        this.elevatorsStatus = ElevatorSystemStatus.stopping;
        this.upRequests.clear();
        this.downRequests.clear();
      }
    }
  }

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  @Override
  public int getNumberOfFloors() {
    return this.numberOfFloors;
  }

  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  @Override
  public int getNumberOfElevators() {
    return this.numberOfElevators;
  }

  /**
   * This method is used to get the capacity of the elevators in the building.
   *
   * @return the capacity of the elevators in the building
   */
  @Override
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  /**
   * This method is used to get the status of the elevator system.
   *
   * @return the status of the elevator system
   */
  @Override
  public BuildingReport getStatusElevatorSystem() {
    ElevatorReport[] elevatorReports = new ElevatorReport[this.numberOfElevators];

    for (int i = 0; i < this.numberOfElevators; ++i) {
      elevatorReports[i] = this.elevators[i].getElevatorStatus();
    }

    return new BuildingReport(this.numberOfFloors,
        this.numberOfElevators,
        this.elevatorCapacity,
        elevatorReports,
        this.upRequests,
        this.downRequests,
        this.elevatorsStatus);
  }

  /**
   * This method is used to add a request to the elevator system.
   *
   * @param request the request to be added to the elevator system
   */
  @Override
  public void addRequestToElevatorSystem(Request request) {
    if (this.elevatorsStatus != ElevatorSystemStatus.outOfService
        && this.elevatorsStatus != ElevatorSystemStatus.stopping) {
      if (request == null) {
        throw new IllegalArgumentException("Request cannot be null");
      } else if (request.getStartFloor() >0 && request.getEndFloor() < this.numberOfElevators) {
        if (request.getEndFloor() >0 && request.getEndFloor() < this.numberOfElevators) {
          if (request.getStartFloor() == request.getEndFloor()) {
            throw new IllegalArgumentException("Start floor and end floor cannot be the same");
          } else {
            if (request.getStartFloor() < request.getEndFloor()) {
              this.upRequests.add(request);
            } else {
              this.downRequests.add(request);
            }
          }
        } else {
          throw new IllegalArgumentException("End floor must be between 0 and "
              + (this.numberOfFloors - 1));
        }
      } else {
        throw new IllegalArgumentException("Start floor must be between 0 and "
            + (this.numberOfFloors - 1));
      }
    } else {
      throw new IllegalStateException("Elevator system not accepting requests.");
    }
  }
}


