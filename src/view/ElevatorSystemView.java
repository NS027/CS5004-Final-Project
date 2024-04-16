package view;

import controller.ElevatorControllerImpl;
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
  private JPanel elavaatorPanel;
  private JPanel displayPanel;
  private List<JTextArea> displayPanelTextAreas = new ArrayList<>();
  private JPanel testPanel;
  private JTextField nStepTextField;
  private JTextField nRequestTextField;

  private Map<Integer, JPanel> elevatorShafts;
  private Map<Integer, JPanel> elevatorCars;
  private JButton stepButton, startButton, stopButton;
  private JPanel addRequestPanel;
  private JLabel selectedStartFloorLabel;
  private JLabel selectedEndFloorLabel;
  private JButton sendRequestButton;
  private JButton nStepButton;
  private JButton nRequestButton;

  // Constructor
  public ElevatorSystemView(ElevatorControllerImpl controller) {
    super("Elevator System Simulation");
    this.controller = controller;

    // Initialize elevatorShafts and elevatorCars
    elevatorShafts = new HashMap<>();
    elevatorCars = new HashMap<>();

    // Panel creation
    createMonitorPanel();
    createControlPanel();
    createElevatorPanel(controller.getBuildingStatus().getNumberOfFloors(), controller.getBuildingStatus().getNumberOfElevators());
    createDisplayPanel(controller.getBuildingStatus().getNumberOfElevators());
    createTestPanel();

    // Configure main frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1024, 800)); // Adjust as needed
    pack(); // Adjusts the frame to fit the content
    setLocationRelativeTo(null); // Center the frame
    setVisible(true);
  }

  private void createMonitorPanel() {
    monitorPanel = new JPanel();
    monitorPanel.setBorder(BorderFactory.createTitledBorder("Monitor Panel"));
    monitorPanelTextArea = new JTextArea(5, 20);
    monitorPanelTextArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(monitorPanelTextArea);
    monitorPanel.add(scrollPane);
    add(monitorPanel, BorderLayout.NORTH);
  }

  private void createControlPanel() {
    controlPanel = new JPanel();
    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
    startButton = new JButton("Start");
    startButton.addActionListener(e -> {
      controller.startSystem();
    });

    stepButton = new JButton("Step");
    stepButton.addActionListener(e -> {
      controller.stepSystem();
    });

    stopButton = new JButton("Stop");
    stopButton.addActionListener(e -> {
      controller.stopSystem();
    });
    controlPanel.add(stepButton);
    controlPanel.add(startButton);
    controlPanel.add(stopButton);
    add(controlPanel, BorderLayout.SOUTH);

    // Adding floor selection drop-downs
    JComboBox<Integer> fromFloorDropdown = new JComboBox<>();
    JComboBox<Integer> toFloorDropdown = new JComboBox<>();
    for (int i = 1; i <= controller.getBuildingStatus().getNumberOfFloors(); i++) {
      fromFloorDropdown.addItem(i);
      toFloorDropdown.addItem(i);
    }

    // Initialize send request button
    JButton sendRequestButton = new JButton("Send Request");
    sendRequestButton.addActionListener(e -> {
      int fromFloor = (int) fromFloorDropdown.getSelectedItem();
      int toFloor = (int) toFloorDropdown.getSelectedItem();
      controller.addRequest(fromFloor, toFloor);
      JOptionPane.showMessageDialog(this, "Request sent from floor " + fromFloor + " to floor " + toFloor);
    });

    // Layout management
    controlPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);  // top, left, bottom, right padding
    gbc.anchor = GridBagConstraints.LINE_START;

    // Add components to the panel
    controlPanel.add(startButton, gbc);
    gbc.gridy++;
    controlPanel.add(stepButton, gbc);
    gbc.gridy++;
    controlPanel.add(stopButton, gbc);

    // Adding floor selection components
    gbc.gridx = 1;  // Increment x to add in next column
    gbc.gridy = 0;
    controlPanel.add(new JLabel("From Floor:"), gbc);
    gbc.gridy++;
    controlPanel.add(fromFloorDropdown, gbc);
    gbc.gridy++;
    controlPanel.add(new JLabel("To Floor:"), gbc);
    gbc.gridy++;
    controlPanel.add(toFloorDropdown, gbc);

    // Send request button
    gbc.gridx = 2;  // Move to the next column for button
    gbc.gridy = 0;
    gbc.gridheight = GridBagConstraints.REMAINDER;  // Span across all remaining rows
    controlPanel.add(sendRequestButton, gbc);

    add(controlPanel, BorderLayout.SOUTH);
  }

  private void createElevatorPanel(int numFloors, int numElevators) {
    elavaatorPanel = new JPanel();
    elavaatorPanel.setBorder(BorderFactory.createTitledBorder("Monitor Panel"));
    // Method to create the panel that demonstrates the elevator shafts and cars
  }

  private void createDisplayPanel(int numElevators) {
    displayPanel = new JPanel();
    displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
    displayPanel.setBorder(BorderFactory.createTitledBorder("Display Panel"));
    for (int i = 0; i < numElevators; i++) {
      JTextArea elevatorReportTextArea = new JTextArea(5, 20);
      elevatorReportTextArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(elevatorReportTextArea);
      displayPanel.add(scrollPane);
      displayPanelTextAreas.add(elevatorReportTextArea);
    }
    add(displayPanel, BorderLayout.CENTER);
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

    add(testPanel, BorderLayout.EAST);

    // Adding action listeners for the buttons
    nStepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int steps = Integer.parseInt(nStepTextField.getText());
          controller.stepSystemN(steps);
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
          JOptionPane.showMessageDialog(ElevatorSystemView.this, "Generated " + requests + " random requests.");
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(ElevatorSystemView.this, "Invalid number for N Requests", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }
}
