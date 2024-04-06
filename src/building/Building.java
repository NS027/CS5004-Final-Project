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
   * throws IllegalArgumentException if floor is less than 2, elevator  or capacity is less than 1
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity)
      throws IllegalArgumentException {
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
  }

  /**
   * This method is used to start the elevator system.
   *
   * @throws IllegalStateException if the elevator system is stopping
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
        this.elevatorsStatus = ElevatorSystemStatus.running;
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
   * This method is used to get the status of the elevators in the building.
   */
  @Override
  public String getElevatorStatus() {
    return this.elevatorsStatus.toString();
  }

  /**
   * This method is used to add a request to the elevator system.
   *
   * @param request the request to be added to the elevator system
   * @throws IllegalArgumentException if the request is null,
   *                                 the start floor is less than 0
   *                                 or greater than or equal to the number of floors,
   *                                 or the elevator system is not accepting requests.
   */
  @Override
  public void addRequestToElevatorSystem(Request request) {
    if (this.elevatorsStatus != ElevatorSystemStatus.outOfService
        && this.elevatorsStatus != ElevatorSystemStatus.stopping) {
      if (request == null) {
        throw new IllegalArgumentException("Request cannot be null");
      } else if (request.getStartFloor() >= 0 && request.getStartFloor() < this.numberOfFloors) {
        if (request.getEndFloor() >= 0 && request.getEndFloor() < this.numberOfFloors) {
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

  /**
   * This method is used to step the elevator system.
   */
  @Override
  public void stepElevatorSystem() {
    if (this.elevatorsStatus != ElevatorSystemStatus.outOfService) {
      if (this.elevatorsStatus != ElevatorSystemStatus.stopping) {
        this.distributeRequests();
      }
    }

    ElevatorInterface[] variable1 = this.elevators;
    int variable2 = variable1.length;

    for (int variable3 = 0; variable3 < variable2; ++variable3) {
      ElevatorInterface elevator = variable1[variable3];
      elevator.step();
    }

    if (this.elevatorsStatus == ElevatorSystemStatus.stopping) {
      boolean allElevatorsOnGroundFloor = true;
      ElevatorInterface[] variable4 = this.elevators;
      int variable5 = variable4.length;

      for (int variable6 = 0; variable6 < variable5; ++variable6) {
        ElevatorInterface elevator = variable4[variable6];
        if (elevator.getCurrentFloor() != 0) {
          allElevatorsOnGroundFloor = false;
          break;
        }
      }

      if (allElevatorsOnGroundFloor) {
        this.elevatorsStatus = ElevatorSystemStatus.outOfService;
      }
    }
  }

  /**
   * This method is used to distribute requests to the elevators.
   */
  private void distributeRequests() {
    if (!this.upRequests.isEmpty() || !this.downRequests.isEmpty()) {
      ElevatorInterface[] variable1 = this.elevators;
      int variable2 = variable1.length;

      for (int variable3 = 0; variable3 < variable2; ++variable3) {
        ElevatorInterface elevator = variable1[variable3];
        if (elevator.isTakingRequests()) {
          List<Request> downRequestsForElevator;
          if (elevator.getCurrentFloor() == 0) {
            downRequestsForElevator = this.getRequests(this.upRequests);
            elevator.processRequests(downRequestsForElevator);
          } else if (elevator.getCurrentFloor() == this.numberOfFloors - 1) {
            downRequestsForElevator = this.getRequests(this.downRequests);
            elevator.processRequests(downRequestsForElevator);
          }
        }
      }
    }
  }

  /**
   * This method is used to get the requests for the elevator.
   *
   * @param requests the list of requests to get the requests from
   * @return the list of requests for the elevator
   */
  private List<Request> getRequests(List<Request> requests) {
    List<Request> requestsForElevator = new ArrayList<>();
    while (!requests.isEmpty() && requestsForElevator.size() < this.elevatorCapacity) {
      requestsForElevator.add(requests.remove(0));
    }

    return requestsForElevator;
  }
}


