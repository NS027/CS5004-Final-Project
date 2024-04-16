package main;

import controller.ElevatorControllerImpl;
import view.ElevatorSystemView;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    // Parameters for the elevator system
    final int numFloors = 10;
    final int numElevators = 2;
    final int capacity = 3;

    // Create the controller with the building parameters
    ElevatorControllerImpl controller = new ElevatorControllerImpl(numFloors, numElevators, capacity);

    // Start the view and pass the controller to it
    SwingUtilities.invokeLater(() -> new ElevatorSystemView(controller));
  }
}


