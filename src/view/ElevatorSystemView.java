package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElevatorSystemView extends JFrame {

  private JPanel buildingReportPanel;
  private Map<Integer, JPanel> elevatorShafts;
  private Map<Integer, JPanel> elevatorCars;
  private JButton stepButton, startButton, stopButton;
  private JPanel addRequestPanel;
  private JTextArea buildingReportTextArea;
  private List<JTextArea> elevatorReportTextAreas = new ArrayList<>();
  private JLabel selectedStartFloorLabel;
  private JLabel selectedEndFloorLabel;
  private JButton sendRequestButton;
  private JPanel elevatorReportPanel;
  private JTextField nStepTextField;
  private JTextField nRequestTextField;
  private JButton nStepButton;
  private JButton nRequestButton;

  // Constructor
  public ElevatorSystemView(int numFloors, int numElevators, int elevatorCapacity) {
    super("Elevator System Simulation");

    // Initialize elevatorShafts and elevatorCars
    elevatorShafts = new HashMap<>();
    elevatorCars = new HashMap<>();

    // Panel creation
    createMonitorPanel();
    createControlPanel();
    createElevatorPanel(numFloors, numElevators);
    createDisplayPanel(numElevators);
    createTestPanel();

    // Configure main frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(800, 600)); // Adjust as needed
    pack(); // Adjusts the frame to fit the content
    setLocationRelativeTo(null); // Center the frame
    setVisible(true);
  }

  private void createMonitorPanel() {
    buildingReportPanel = new JPanel();
    buildingReportPanel.setBorder(BorderFactory.createTitledBorder("Building Report"));
    buildingReportTextArea = new JTextArea(5, 20);
    buildingReportTextArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(buildingReportTextArea);
    buildingReportPanel.add(scrollPane);
    add(buildingReportPanel, BorderLayout.NORTH);
  }

  private void createControlPanel() {
    JPanel controlPanel = new JPanel();
    stepButton = new JButton("Step");
    startButton = new JButton("Start");
    stopButton = new JButton("Stop");
    controlPanel.add(stepButton);
    controlPanel.add(startButton);
    controlPanel.add(stopButton);
    add(controlPanel, BorderLayout.SOUTH);
  }

  private void createElevatorPanel(int numFloors, int numElevators) {
    // Method to create the panel that demonstrates the elevator shafts and cars
  }

  private void createDisplayPanel(int numElevators) {
    elevatorReportPanel = new JPanel();
    elevatorReportPanel.setLayout(new BoxLayout(elevatorReportPanel, BoxLayout.Y_AXIS));
    elevatorReportPanel.setBorder(BorderFactory.createTitledBorder("Elevator Reports"));
    for (int i = 0; i < numElevators; i++) {
      JTextArea elevatorReportTextArea = new JTextArea(5, 20);
      elevatorReportTextArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(elevatorReportTextArea);
      elevatorReportPanel.add(scrollPane);
      elevatorReportTextAreas.add(elevatorReportTextArea);
    }
    add(elevatorReportPanel, BorderLayout.CENTER);
  }

  private void createTestPanel() {
    JPanel testPanel = new JPanel();
    // Rest of your existing code for testPanel
  }

  // Main method to launch the UI
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new ElevatorSystemView(10, 3, 5); // Example parameters
      }
    });
  }
}
