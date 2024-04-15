package view;

import controller.ElevatorControllerImpl;
import building.BuildingReport;
import java.util.List;
import scanerzus.Request;
import elevator.ElevatorReport;
import java.awt.*;
import javax.swing.*;
import scanerzus.Request;

public class ElevatorSystemView extends JFrame {

  private ElevatorControllerImpl controller;
  private JPanel floorPanel;
  private JLabel statusLabel;
  private JComboBox<String> startFloorDropdown, endFloorDropdown;
  private JButton startButton, stepButton, stopButton, nStepButton, sendRequestButton, nRequestsButton;
  private JTextField nStepTextField, nRequestsTextField;
  private JTextArea statusArea;

  public ElevatorSystemView(ElevatorControllerImpl controller) {
    this.controller = controller;
    initializeUI();
  }

  private void initializeUI() {
    setTitle("Elevator System Simulation");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel(new BorderLayout());
    floorPanel = new JPanel(new GridLayout(0, 5, 5, 5)); // 5 elevators assumed
    statusLabel = new JLabel("System Status: Stopped");

    setupDropdowns();
    setupButtons();
    setupStatusArea();

    mainPanel.add(statusLabel, BorderLayout.NORTH);
    mainPanel.add(floorPanel, BorderLayout.CENTER);
    mainPanel.add(createControlPanel(), BorderLayout.SOUTH);
    add(mainPanel);

    setVisible(true);
    updateViewStatus();  // Initial update of the view status
  }

  private JPanel createControlPanel() {
    JPanel controlPanel = new JPanel();
    controlPanel.add(startFloorDropdown);
    controlPanel.add(endFloorDropdown);
    controlPanel.add(startButton);
    controlPanel.add(stepButton);
    controlPanel.add(stopButton);
    controlPanel.add(nStepTextField);
    controlPanel.add(nStepButton);
    controlPanel.add(sendRequestButton);
    controlPanel.add(nRequestsTextField);
    controlPanel.add(nRequestsButton);
    return controlPanel;
  }

  private void setupDropdowns() {
    startFloorDropdown = new JComboBox<>();
    endFloorDropdown = new JComboBox<>();
    for (int i = 0; i < controller.getBuildingStatus().getNumberOfFloors(); i++) {
      startFloorDropdown.addItem(String.valueOf(i));
      endFloorDropdown.addItem(String.valueOf(i));
    }
  }

  private void setupButtons() {
    startButton = new JButton("Start");
    startButton.addActionListener(e -> {
      controller.startSystem();
      updateViewStatus();
    });

    stepButton = new JButton("Step");
    stepButton.addActionListener(e -> {
      controller.stepSystem();
      updateViewStatus();
    });

    stopButton = new JButton("Stop");
    stopButton.addActionListener(e -> {
      controller.stopSystem();
      updateViewStatus();
    });

    nStepTextField = new JTextField(5);
    nStepButton = new JButton("N Steps");
    nStepButton.addActionListener(e -> {
      int steps = Integer.parseInt(nStepTextField.getText());
      controller.stepSystemN(steps);
      updateViewStatus();
    });

    sendRequestButton = new JButton("Send Request");
    sendRequestButton.addActionListener(e -> {
      int fromFloor = Integer.parseInt((String) startFloorDropdown.getSelectedItem());
      int toFloor = Integer.parseInt((String) endFloorDropdown.getSelectedItem());
      controller.addRequest(fromFloor, toFloor);
      updateViewStatus();
    });

    nRequestsTextField = new JTextField(5);
    nRequestsButton = new JButton("N Requests");
    nRequestsButton.addActionListener(e -> {
      int requests = Integer.parseInt(nRequestsTextField.getText());
      controller.addRandomRequest(requests);
      updateViewStatus();
    });
  }

  private void setupStatusArea() {
    statusArea = new JTextArea(10, 40);
    statusArea.setEditable(false);
    // Add statusArea to the panel or scroll pane if required
    // ...
  }

  public void updateViewStatus() {
    BuildingReport report = controller.getBuildingReport();
    statusLabel.setText("System Status: " + report.getSystemStatus());
    statusArea.setText("");  // Clear previous text

    floorPanel.removeAll(); // Clear previous elevator representations

    List<Request> requests = controller.getActiveRequests();  // Get active requests directly from the controller

    for (ElevatorReport elevatorReport : report.getElevatorReports()) {
      JPanel elevatorPanel = new JPanel();
      elevatorPanel.setLayout(new BoxLayout(elevatorPanel, BoxLayout.Y_AXIS));
      elevatorPanel.setBorder(BorderFactory.createTitledBorder("Elevator " + elevatorReport.getElevatorId()));

      JLabel floorLabel = new JLabel("Floor: " + elevatorReport.getCurrentFloor());
      JLabel directionLabel = new JLabel("Direction: " + elevatorReport.getDirection());
      JLabel doorStatusLabel = new JLabel("Door: " + (elevatorReport.isDoorClosed() ? "Closed" : "Open"));

      elevatorPanel.add(floorLabel);
      elevatorPanel.add(directionLabel);
      elevatorPanel.add(doorStatusLabel);

      // Display all active requests
      StringBuilder requestText = new StringBuilder("<html>Active Requests:<br/>");
      for (Request request : requests) {
        requestText.append("[").append(request.getStartFloor()).append(" -> ").append(request.getEndFloor()).append("]<br/>");
      }
      requestText.append("</html>");
      JLabel requestLabel = new JLabel(requestText.toString());
      elevatorPanel.add(requestLabel);

      floorPanel.add(elevatorPanel);
    }

    floorPanel.revalidate(); // Revalidate the panel to reflect changes
    floorPanel.repaint();    // Repaint the panel to display the updated status
  }

}
