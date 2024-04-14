package view;

import controller.ElevatorControllerImpl;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ElevatorSystemView extends JFrame {
  private final ElevatorControllerImpl controller;
  private JLabel statusLabel;
  private JComboBox<Integer> startFloorDropdown;
  private JComboBox<Integer> endFloorDropdown;

  /**
   * Constructor for the ElevatorSystemView class
   *
   * @param controller the controller for the elevator system
   */
  public ElevatorSystemView(ElevatorControllerImpl controller) {
    this.controller = controller;
    initializeUI();
  }

  /**
   * This is method used to initialize the UI
   */
  private void initializeUI() {

  }

}
