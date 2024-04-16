package view;

import controller.ElevatorControllerImpl;
import elevator.ElevatorReport;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElevatorSystemView extends JFrame {
  private ElevatorControllerImpl controller;
  private JPanel monitorPanel;
  private JTextArea monitorPanelTextArea;
  private JPanel controlPanel;
  private JPanel demonstratePanel;
  private JPanel displayPanel;
  private List<JTextArea> displayPanelTextAreas = new ArrayList<>();
  private JPanel testPanel;
  private JTextField nStepTextField;
  private JTextField nRequestTextField;

  private JLabel floorInfoLabel;
  private JLabel systemStatusLabel;

  private Map<Integer, JPanel> elevatorShafts;
  private Map<Integer, JPanel> elevatorCars;
  private JButton stepButton;
  private JButton startButton;
  private JButton stopButton;
  private JButton nStepButton;
  private JButton exitButton;
  private JButton nRequestButton;
  private JButton sendRequestButton;

  private JPanel addRequestPanel;
  private JLabel selectedStartFloorLabel;
  private JLabel selectedEndFloorLabel;


  // Constructor
  public ElevatorSystemView(ElevatorControllerImpl controller) {
    super("Elevator System Simulation");
    this.controller = controller;

    // Initialize elevatorShafts and elevatorCars
    elevatorShafts = new HashMap<>();
    elevatorCars = new HashMap<>();

    // Panel creation methods
    createMonitorPanel();
    createControlPanel();
    createDemonstratePanel(controller.getBuildingStatus().getNumberOfFloors(),
        controller.getBuildingStatus().getNumberOfElevators());
    createDisplayPanel(controller.getBuildingStatus().getNumberOfElevators());
    createTestPanel();

    // Some update methods
    updateMonitorPanel();
    updateElevatorDisplays();
    for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
      ElevatorReport report = controller.getElevatorReport(i);
      updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
    }

    // Configure main frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1024, 800)); // Adjust as needed
    pack(); // Adjusts the frame to fit the content
    setLocationRelativeTo(null); // Center the frame
    setVisible(true);
  }

  private void createMonitorPanel() {
    monitorPanel = new JPanel();
    monitorPanel.setLayout(new BoxLayout(monitorPanel, BoxLayout.Y_AXIS));
    monitorPanel.setBorder(BorderFactory.createTitledBorder("Monitor Panel"));

    monitorPanelTextArea = new JTextArea(10, 20);
    monitorPanelTextArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(monitorPanelTextArea);

    // Set the initial text to the building report
    monitorPanelTextArea.setText(controller.getBuildingReport().toString());

    monitorPanel.add(scrollPane);
    add(monitorPanel, BorderLayout.NORTH);
  }


  public void updateMonitorPanel() {
    SwingUtilities.invokeLater(() -> {
      monitorPanelTextArea.setText(controller.getBuildingReport().toString());
    });
  }

  private void createControlPanel() {
    controlPanel = new JPanel();
    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
    controlPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    // Initialize buttons
    startButton = new JButton("Start");
    startButton.addActionListener(e -> {
      controller.startSystem();
      updateMonitorPanel();
      updateElevatorDisplays();
      for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
        ElevatorReport report = controller.getElevatorReport(i);
        updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
      }
    });

    stepButton = new JButton("Step");
    stepButton.addActionListener(e -> {
      controller.stepSystem();
      updateMonitorPanel();
      updateElevatorDisplays();
      for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
        ElevatorReport report = controller.getElevatorReport(i);
        updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
      }
    });

    stopButton = new JButton("Stop");
    stopButton.addActionListener(e -> {
      controller.stopSystem();
      updateMonitorPanel();
      updateElevatorDisplays();
      for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
        ElevatorReport report = controller.getElevatorReport(i);
        updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
      }
    });

    exitButton = new JButton("Exit");
    exitButton.addActionListener(e -> {
      controller.quitSystem();
    });



    // Initialize dropdowns
    JComboBox<Integer> fromFloorDropdown = new JComboBox<>();
    JComboBox<Integer> toFloorDropdown = new JComboBox<>();
    for (int i = 1; i <= controller.getBuildingStatus().getNumberOfFloors(); i++) {
      fromFloorDropdown.addItem(i);
      toFloorDropdown.addItem(i);
    }

    // Initialize send request button
    sendRequestButton = new JButton("Send Request");
    sendRequestButton.addActionListener(e -> {
      int fromFloor = (int) fromFloorDropdown.getSelectedItem();
      int toFloor = (int) toFloorDropdown.getSelectedItem();
      controller.addRequest(fromFloor, toFloor);
      updateMonitorPanel();
      updateElevatorDisplays();
      for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
        ElevatorReport report = controller.getElevatorReport(i);
        updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
      }
      JOptionPane.showMessageDialog(this, "Request sent from floor " + fromFloor + " to floor " + toFloor);
    });

    // GridBagLayout settings
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;  // This auto-increments the gridy value
    gbc.fill = GridBagConstraints.HORIZONTAL; // Make the component fill its display area horizontally
    gbc.insets = new Insets(5, 5, 5, 5); // Padding

    // Add components to the panel
    controlPanel.add(startButton, gbc);
    controlPanel.add(stepButton, gbc);
    controlPanel.add(stopButton, gbc);
    controlPanel.add(exitButton, gbc);
    controlPanel.add(new JLabel("From Floor:"), gbc);
    controlPanel.add(fromFloorDropdown, gbc);
    controlPanel.add(new JLabel("To Floor:"), gbc);
    controlPanel.add(toFloorDropdown, gbc);
    controlPanel.add(sendRequestButton, gbc);

    add(controlPanel, BorderLayout.EAST);
  }

  private void createDemonstratePanel(int numFloors, int numElevators) {
    JPanel elevatorsPanel = new JPanel(new GridLayout(1, numElevators, 10, 0));
    elevatorShafts = new HashMap<>();
    elevatorCars = new HashMap<>();

    for (int i = 0; i < numElevators; i++) {
      JPanel elevatorShaft = new JPanel(new GridLayout(numFloors, 1));
      elevatorShaft.setBorder(BorderFactory.createTitledBorder("Elevator " + (i + 1)));

      for (int j = numFloors - 1; j >= 0; j--) {
        JPanel floorPanel = new JPanel();
        floorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        floorPanel.setLayout(new BorderLayout());

        JLabel floorLabel = new JLabel("Floor " + (j + 1), SwingConstants.CENTER);
        floorPanel.add(floorLabel, BorderLayout.NORTH);

        if (j == 0) {  // Assuming elevator starts at the bottom floor
          JPanel elevatorCar = new JPanel();
          elevatorCar.setBackground(Color.LIGHT_GRAY);
          elevatorCar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
          elevatorCars.put(i, elevatorCar);
          floorPanel.add(elevatorCar, BorderLayout.CENTER);
        }

        elevatorShaft.add(floorPanel);
      }
      elevatorsPanel.add(elevatorShaft);
      elevatorShafts.put(i, elevatorShaft);
    }
    add(elevatorsPanel, BorderLayout.CENTER);
  }

  public void updateElevatorPosition(int elevatorId, int currentFloor, boolean doorOpen) {
    JPanel elevatorCar = elevatorCars.get(elevatorId);
    if (elevatorCar == null) {
      System.err.println("Elevator car not found for ID: " + elevatorId);
      return;
    }

    JPanel elevatorShaft = elevatorShafts.get(elevatorId);
    for (Component comp : elevatorShaft.getComponents()) {
      ((JPanel) comp).remove(elevatorCar);
    }

    JPanel floorPanel = (JPanel) elevatorShaft.getComponent(controller.getBuildingStatus().getNumberOfFloors() - currentFloor - 1);
    elevatorCar.setBackground(doorOpen ? Color.GREEN : Color.LIGHT_GRAY);
    floorPanel.add(elevatorCar, BorderLayout.CENTER);
    elevatorShaft.revalidate();
    elevatorShaft.repaint();
  }

  private void createDisplayPanel(int numElevators) {
    displayPanel = new JPanel();
    displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
    displayPanel.setBorder(BorderFactory.createTitledBorder("Display Panel"));
    displayPanelTextAreas = new ArrayList<>(); // Initialize the list of JTextAreas

    for (int i = 0; i < numElevators; i++) {
      JPanel elevatorPanel = new JPanel();
      elevatorPanel.setLayout(new BoxLayout(elevatorPanel, BoxLayout.Y_AXIS));
      elevatorPanel.setBorder(BorderFactory.createTitledBorder("Elevator " + (i + 1)));

      JTextArea elevatorReportTextArea = new JTextArea(5, 20);
      elevatorReportTextArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(elevatorReportTextArea);
      elevatorPanel.add(scrollPane);
      displayPanel.add(elevatorPanel);

      displayPanelTextAreas.add(elevatorReportTextArea); // Add the text area to the list
    }
    add(displayPanel, BorderLayout.WEST);
  }

  private void updateElevatorDisplays() {
    SwingUtilities.invokeLater(() -> {
      for (int i = 0; i < displayPanelTextAreas.size(); i++) {
        JTextArea textArea = displayPanelTextAreas.get(i);
        ElevatorReport report = controller.getElevatorReport(i);
        if (report != null) {
          textArea.setText(report.toString());
        }
      }
    });
  }

  private void createTestPanel() {
    testPanel = new JPanel();
    testPanel.setBorder(BorderFactory.createTitledBorder("Test Panel"));
    testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS)); // Align vertically

    // N Steps components
    JPanel nStepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    nStepPanel.add(new JLabel("N Steps:"));
    nStepTextField = new JTextField(10);
    nStepPanel.add(nStepTextField);
    nStepButton = new JButton("Perform N Steps");
    nStepPanel.add(nStepButton);
    testPanel.add(nStepPanel);

    // N Requests components
    JPanel nRequestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    nRequestPanel.add(new JLabel("N Requests:"));
    nRequestTextField = new JTextField(10);
    nRequestPanel.add(nRequestTextField);
    nRequestButton = new JButton("Generate N Requests");
    nRequestPanel.add(nRequestButton);
    testPanel.add(nRequestPanel);

    add(testPanel, BorderLayout.SOUTH);

    // Adding action listeners for the buttons
    nStepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int steps = Integer.parseInt(nStepTextField.getText());
          controller.stepSystemN(steps);
          updateMonitorPanel();
          updateElevatorDisplays();
          for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
            ElevatorReport report = controller.getElevatorReport(i);
            updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
          }
          JOptionPane.showMessageDialog(ElevatorSystemView.this, "Performed " + steps + " steps.");
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(ElevatorSystemView.this, "Invalid number for N Steps", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    nRequestButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int requests = Integer.parseInt(nRequestTextField.getText());
          controller.addRandomRequest(requests);
          updateMonitorPanel();
          updateElevatorDisplays();
          for (int i = 0; i < controller.getBuildingStatus().getNumberOfElevators(); i++) {
            ElevatorReport report = controller.getElevatorReport(i);
            updateElevatorPosition(i, report.getCurrentFloor(), report.isDoorClosed());
          }
          JOptionPane.showMessageDialog(ElevatorSystemView.this, "Generated " + requests + " random requests.");
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(ElevatorSystemView.this, "Invalid number for N Requests", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }
}
