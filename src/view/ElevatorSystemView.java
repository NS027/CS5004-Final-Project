package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ... Other imports

public class ElevatorSystemView extends JFrame {
  // Existing component declarations...

  // Adding components for the Test Panel
  private JTextField nStepTextField;
  private JTextField nRequestTextField;
  private JButton nStepButton;
  private JButton nRequestButton;

  // Constructor
  public ElevatorSystemView(int numFloors, int numElevators) {
    // Existing initialization code...

    // Monitor Panel
    JPanel monitorPanel = createMonitorPanel();

    // Control Panel
    JPanel controlPanel = createControlPanel();

    // Display Panel
    JPanel displayPanel = createDisplayPanel();

    // Test Panel
    JPanel testPanel = createTestPanel();

    // Set the layout and add the panels to the frame
    setLayout(new BorderLayout());
    add(monitorPanel, BorderLayout.NORTH);
    add(controlPanel, BorderLayout.WEST);
    add(displayPanel, BorderLayout.CENTER);
    add(testPanel, BorderLayout.SOUTH);

    // Finalize setup
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack(); // Adjusts the frame to fit the content
    setLocationRelativeTo(null); // Center the frame
    setVisible(true);
  }

  // Monitor Panel
  private JPanel createMonitorPanel() {
    // Assuming monitorPanel is a JPanel with a JTextArea that updates to show the building report
    // The JTextArea updates are assumed to be handled elsewhere in the code based on simulation updates
    return monitorPanel;
  }

  // Control Panel
  private JPanel createControlPanel() {
    // Existing method to create the control panel with Start, Step, and Stop buttons
    // The button actions are assumed to be handled elsewhere in the code to control the simulation
    return controlPanel;
  }

  // Display Panel
  private JPanel createDisplayPanel() {
    // This panel can be made up of JTextAreas within JScrollPane for each elevator
    // Each JTextArea can be updated based on the individual elevator's state changes
    return displayPanel;
  }

  // Test Panel
  private JPanel createTestPanel() {
    JPanel testPanel = new JPanel(new GridLayout(2, 3, 5, 5));

    testPanel.add(new JLabel("N Steps:"));
    nStepTextField = new JTextField();
    testPanel.add(nStepTextField);

    nStepButton = new JButton("Perform N Steps");
    testPanel.add(nStepButton);

    testPanel.add(new JLabel("N Requests:"));
    nRequestTextField = new JTextField();
    testPanel.add(nRequestTextField);

    nRequestButton = new JButton("Generate N Requests");
    testPanel.add(nRequestButton);

    // Adding action listeners for the buttons
    nStepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Logic to perform N Steps
      }
    });

    nRequestButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Logic to generate N Requests
      }
    });

    return testPanel;
  }

  // Existing methods for updating the UI based on simulation...

  // Main method to launch the UI
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new ElevatorSystemView(10, 3); // Example floor and elevator numbers
      }
    });
  }
}

