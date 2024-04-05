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

}
