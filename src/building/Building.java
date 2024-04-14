package building;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.Iterator;
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

      // The elevator system is out of service until it is started.
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
    // Check if the elevator system is already running.
    if (this.elevatorsStatus == ElevatorSystemStatus.running) {
      System.out.println("System is already running");
      return;
    }

    // When system is in the process of stopping, it cannot be started.
    if (this.elevatorsStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("Elevator cannot be started until it is stopped");
    }

    // If the elevator system is initiated or at out of service status, start the elevators.
    if (this.elevatorsStatus == ElevatorSystemStatus.outOfService) {
      for (ElevatorInterface elevator : this.elevators) {
        elevator.start();  // Start each elevator individually
      }
      this.elevatorsStatus = ElevatorSystemStatus.running;  // Change system status to running
    }
  }

  /**
   * This method is used to stop the elevator system.
   */
  @Override
  public void stopElevatorSystem() {
    // If the elevator is not running, it cannot be stopped.
    if (this.elevatorsStatus == ElevatorSystemStatus.running) {
      this.elevatorsStatus = ElevatorSystemStatus.stopping;
      System.out.println("Elevator system is stopping, no new requests will be accepted.");

      // Command each elevator to stopping status
      for (ElevatorInterface elevator : this.elevators) {
        elevator.takeOutOfService();
      }
      // Set the status of the elevator system to stopping
      this.elevatorsStatus = ElevatorSystemStatus.stopping;

      // Clear the requests
      this.upRequests.clear();
      this.downRequests.clear();

      // Check the status of each elevator to complete the stopping process
      completeShutDown();
    } else if (this.elevatorsStatus == ElevatorSystemStatus.outOfService) {
      System.out.println("Elevator system is already out of service.");
    } else if (this.elevatorsStatus == ElevatorSystemStatus.stopping) {
      System.out.println("Elevator system is already stopping.");
    }
  }

  /**
   * This method is used to check the status of each elevator to complete the stopping process.
   * If all elevators are on the ground floor, the elevator system is out of service.
   * If not, the elevator system is still stopping.
   */
  private void completeShutDown() {
    boolean allElevatorsOnGroundFloor = true;  // Reset the flag each time method is called
    for (ElevatorInterface elevator : this.elevators) {
      if (elevator.getCurrentFloor() != 0) {
        allElevatorsOnGroundFloor = false;  // Set to false if any elevator is not on the ground floor
        break;
      }
    }

    if (allElevatorsOnGroundFloor) {
      for (ElevatorInterface elevator : this.elevators) {
        elevator.step();
      }
      this.elevatorsStatus = ElevatorSystemStatus.outOfService;
      System.out.println("System is now out of service.");
    } else {
      System.out.println("Waiting for all elevators to reach the ground floor.");
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
      if (this.elevatorsStatus == ElevatorSystemStatus.running) {
        this.distributeRequests();
      }

      for (ElevatorInterface elevator : this.elevators) {
        elevator.step();
      }

      // Check if all elevators are on te ground if system is stopping
      if (this.elevatorsStatus == ElevatorSystemStatus.stopping) {
        this.completeShutDown();
      }
    }
  }

  /**
   * This method is used to distribute requests to the elevators.
   * If an elevator is on the ground floor, it will take requests from the upRequests list.
   * If an elevator is on the top floor, it will take requests from the downRequests list.
   */
  private void distributeRequests() {
    // Exit if there are no requests
    if (upRequests.isEmpty() && downRequests.isEmpty()) {
      return;
    }

    // Iterate through the elevators
    for (ElevatorInterface elevator : elevators) {
      if (!elevator.isTakingRequests()) {
        continue;  // Skip the elevator if it is not taking requests
      }

      // Get the requests for the elevator
      List<Request> requestsToProcess = new ArrayList<>();

      // Assign up requests to the elevator if it is on the ground floor
      // or elevator is going up and there are no down requests
      if (elevator.getCurrentFloor() == 0 || elevator.getDirection() == Direction.UP) {
        requestsToProcess.addAll(getRequests(upRequests, elevator.getCurrentFloor(), true));
      }

      // Assign down requests to the elevator if it is on the top floor
      // or elevator is going down and there are no up requests
      if (elevator.getCurrentFloor() == this.numberOfFloors - 1
          || elevator.getDirection() == Direction.DOWN) {
        requestsToProcess.addAll(getRequests(downRequests, elevator.getCurrentFloor(), false));
      }

      // Process all the requests in the list
      if (!requestsToProcess.isEmpty()) {
        elevator.processRequests(requestsToProcess);
      }
    }
  }

  /**
   * This method is used to get the requests for the elevator.
   *
   * @param requests the list of requests to get the requests from
   * @param currentFloor the current floor of the elevator
   * @param isUp true if the elevator is moving up, false if the elevator is moving down
   * @return the list of requests for the elevator
   */
  private List<Request> getRequests(List<Request> requests, int currentFloor, boolean isUp) {
    List<Request> matchedRequests = new ArrayList<>();
    Iterator<Request> iterator = requests.iterator();

    // Loop through each request and determine if it matches the elevator's current situation.
    while (iterator.hasNext()) {
      Request request = iterator.next();
      // For elevators moving up, add requests where the start floor is greater than or equal to the elevator's current floor.
      // For elevators moving down, add requests where the start floor is less than or equal to the elevator's current floor.
      if ((isUp && request.getStartFloor() >= currentFloor)
          || (!isUp && request.getStartFloor() <= currentFloor)) {
        matchedRequests.add(request);
        // Remove the request from the main list to prevent it from being processed again.
        iterator.remove();
      }
    }
    return matchedRequests;
  }
}


