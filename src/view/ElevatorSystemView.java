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
  private JPanel elavatorPanel;
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
    controlPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    // Initialize buttons
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

  private void createElevatorPanel(int numFloors, int numElevators) {
    elavatorPanel = new JPanel();
    elavatorPanel.setBorder(BorderFactory.createTitledBorder("Monitor Panel"));
    elavatorPanel.setLayout(new BoxLayout(elavatorPanel, BoxLayout.X_AXIS));
    add(elavatorPanel, BorderLayout.CENTER);
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
    add(displayPanel, BorderLayout.WEST);
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
