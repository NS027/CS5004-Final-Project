package building;

import static org.junit.Assert.assertEquals;

import building.enums.ElevatorSystemStatus;
import org.junit.Test;
import scanerzus.Request;

/**
 * Test class for the Building class.
 */
public class BuildingTest {
  private Building building;
  private final int numberOfFloors = 15;
  private final int numberOfElevators = 2;
  private final int elevatorCapacity = 5;

  /**
   * Test the constructor for exceptions.
   * numberOfFloors must be greater than or equal to 2
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNumberOfFloorsLessThanTwo() {
    building = new Building(1, numberOfElevators, elevatorCapacity);
  }

  /**
   * Test the constructor for exceptions.
   * numberOfElevators must be greater than or equal to 1
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNumberOfElevatorsLessThanOne() {
    building = new Building(numberOfFloors, 0, elevatorCapacity);
  }

  /**
   * Test the constructor for exceptions.
   * elevatorCapacity must be greater than or equal to 1
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorCapacityLessThanOne() {
    building = new Building(numberOfFloors, numberOfElevators, 0);
  }

  /**
   * Test for getNumberOfFloors.
   */
  @Test
  public void testGetNumberOfFloors() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    assertEquals(numberOfFloors, building.getNumberOfFloors());
  }

  /**
   * Test for getNumberOfElevators.
   */
  @Test
  public void testGetNumberOfElevators() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    assertEquals(numberOfElevators, building.getNumberOfElevators());
  }

  /**
   * Test for getElevatorCapacity.
   */
  @Test
  public void testGetElevatorCapacity() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    assertEquals(elevatorCapacity, building.getElevatorCapacity());
  }

  /**
   * Test for elevator can not be started until it is stopped.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartElevatorSystemWhenElevatorIsStopping() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.stopElevatorSystem();
    building.startElevatorSystem();
  }

  /**
   * Test for startElevatorSystem.
   */
  @Test
  public void testStartElevatorSystem() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running.toString(), building.getElevatorStatus());
  }

  /**
   * Test for stopElevatorSystem.
   */
  @Test
  public void testStopElevatorSystem() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping.toString(), building.getElevatorStatus());
  }

  /**
   * Test for stopElevatorSystem when elevator system is out of service.
   */
  @Test
  public void testStopElevatorSystemWhenElevatorSystemIsOutOfService() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.stopElevatorSystem();
    building.stepElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService.toString(), building.getElevatorStatus());
  }

  /**
   * Test for get request from elevator system.
   * The request is for up direction.
   */
  @Test
  public void testAddRequestToElevatorSystemForUp() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(2, 9));
    assertEquals("[2->9]", building.getStatusElevatorSystem().getUpRequests().toString());
  }

  /**
   * Test for get request from elevator system.
   * The request is for down direction.
   */
  @Test
  public void testAddRequestToElevatorSystemForDown() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(9, 2));
    assertEquals("[9->2]", building.getStatusElevatorSystem().getDownRequests().toString());
  }

  /**
   * Test for get request from elevator system.
   * The request is for up direction and down direction.
   */
  @Test
  public void testAddRequestToElevatorSystemForUpAndDown() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(2, 9));
    building.addRequestToElevatorSystem(new Request(9, 2));
    assertEquals("[2->9]", building.getStatusElevatorSystem().getUpRequests().toString());
    assertEquals("[9->2]", building.getStatusElevatorSystem().getDownRequests().toString());
  }

  /**
   * Test for get request from elevator system.
   * The request is cleared after the elevator system is stopped.
   */
  @Test
  public void testStoppedElevatorSystemStatus() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(2, 9));
    building.addRequestToElevatorSystem(new Request(9, 2));
    building.stopElevatorSystem();
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(0, report.getUpRequests().size());
    assertEquals(0, report.getDownRequests().size());
    assertEquals(ElevatorSystemStatus.stopping, report.getSystemStatus());
  }

  /**
   * addRequestToElevatorSystem method test with exception.
   * The request can not be added after the elevator system is stopped.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestAfterStopped() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.stopElevatorSystem();
    building.addRequestToElevatorSystem(new Request(3, 1));
  }

  /**
   * addRequestToElevatorSystem method test with exception.
   * The request can not be added before the elevator system is started.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestBeforeStarted() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.addRequestToElevatorSystem(new Request(3, 1));
  }

  /**
   * addRequestToElevatorSystem method test with exception.
   * The null request can not be added.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullRequest() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(null);
  }

  /**
   * addRequestToElevatorSystem method test with exception.
   * The request can not be added with invalid start floor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestInvalidStartFloor() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(-5, 1));
  }

  /**
   * addRequestToElevatorSystem method test with exception.
   * The request can not be added with invalid end floor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestInvalidEndFloor() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(1, 100));
  }

  /**
   * addRequestToElevatorSystem method test with exception.
   * The request can not be added with invalid same floor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestWithSameFloor() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(1, 1));
  }

  /**
   * addRequestToElevatorSystem method test.
   */
  @Test
  public void testAddUpRequestToElevatorSystem() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(0, 1));
    building.addRequestToElevatorSystem(new Request(0, 3));
    building.addRequestToElevatorSystem(new Request(0, 5));
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(3, report.getUpRequests().size());
  }

  @Test
  public void testAddUpAndDownRequestToElevatorSystem() {
    building = new Building(numberOfFloors, 6, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(0, 1));
    building.addRequestToElevatorSystem(new Request(0, 3));
    building.addRequestToElevatorSystem(new Request(0, 5));
    building.addRequestToElevatorSystem(new Request(3, 8));
    building.addRequestToElevatorSystem(new Request(6, 1));
    building.addRequestToElevatorSystem(new Request(7, 2));
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(4, report.getUpRequests().size());
    assertEquals(2, report.getDownRequests().size());
  }

  /**
   * Test for the stepElevatorSystem method.
   */
  @Test
  public void testStepElevatorSystem() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(0, 2));
    building.addRequestToElevatorSystem(new Request(5, 1));
    for (int i = 0; i < 10; i++) {
      building.stepElevatorSystem();
    }
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(2, report.getElevatorReports()[0].getCurrentFloor());
    assertEquals(5, report.getElevatorReports()[1].getCurrentFloor());
  }

  /**
   * Test for multiple step method.
   * This is to test the one floor method.
   */
  @Test
  public void testStepOneFloorElevatorSystem() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(0, 1));
    building.stepElevatorSystem();
    building.stepElevatorSystem();
    building.stepElevatorSystem();
    building.stepElevatorSystem();
    building.stepElevatorSystem();
    BuildingReport report = building.getStatusElevatorSystem();
    assertEquals(1, report.getElevatorReports()[0].getCurrentFloor());
  }


  /**
   *  Test for the capacity overload.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testExceedRequestsCapacity() {
    building = new Building(numberOfFloors, 1, 2);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(0, 1));
    building.addRequestToElevatorSystem(new Request(0, 2));
    building.addRequestToElevatorSystem(new Request(0, 3));
    building.stepElevatorSystem();
  }

  /**
   * Test for out of service.
   */
  @Test
  public void testOutOfService() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.stopElevatorSystem();
    building.stepElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService.toString(), building.getElevatorStatus());
  }

  /**
   * Test for take out of service when door is open for at least one elevator
   */
  @Test
  public void testOutOfServiceWhenDoorOpen() {
    building = new Building(numberOfFloors, numberOfElevators, elevatorCapacity);
    building.startElevatorSystem();
    building.addRequestToElevatorSystem(new Request(1, 2));
    building.stepElevatorSystem();
    building.stepElevatorSystem();
    building.stepElevatorSystem();
    building.stopElevatorSystem();
    building.stepElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService.toString(), building.getElevatorStatus());
  }
}