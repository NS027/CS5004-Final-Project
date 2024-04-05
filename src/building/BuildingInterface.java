package building;

import scanerzus.Request;

/**
 * This interface is used to represent a building.
 */
public interface BuildingInterface {

  /**
   * This method is used to get the Report of the elevator system.
   *
   * @return the report of the elevator system.
   */
  BuildingReport getStatusElevatorSystem();

  /**
   * This method is used to add a request to the elevator system.
   *
   * @param variable1 the request to add
   */
  void addRequestToElevatorSystem(Request variable1);

  /**
   * This method is used to start the elevator system.
   */
  void startElevatorSystem();

  /**
   * This method is used to stop the elevator system.
   */
  void stopElevatorSystem();



}
