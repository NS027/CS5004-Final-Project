package view;

import controller.ElevatorControllerImpl;

public class Main {
  public static void main(String[] args) {
    ElevatorControllerImpl controller = new ElevatorControllerImpl(10, 2, 10);
    ElevatorSystemView view = new ElevatorSystemView(controller);
    view.setVisible(true);
  }
}

