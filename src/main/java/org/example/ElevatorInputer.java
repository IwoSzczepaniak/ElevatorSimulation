package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElevatorInputer extends JFrame {
    private final JTextField elevatorField;
    private final JTextField floorField;
    private ElevatorSystem system;

    public ElevatorInputer() {
        setTitle("Elevator System Input");
        setSize(600, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        JLabel elevatorLabel = new JLabel("Number of Elevators:");
        JLabel floorLabel = new JLabel("Highest Floor:");
        elevatorField = new JTextField(10);
        floorField = new JTextField(10);
        JButton submitButton = new JButton("Start Simulation");


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        elevatorLabel.setFont(labelFont);
        floorLabel.setFont(labelFont);
        elevatorField.setFont(fieldFont);
        floorField.setFont(fieldFont);

        panel.add(elevatorLabel);
        panel.add(elevatorField);
        panel.add(floorLabel);
        panel.add(floorField);
        panel.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void startSimulation() {
        try {
            int numElevators = Integer.parseInt(elevatorField.getText());
            int highestFloor = Integer.parseInt(floorField.getText());

            system = new SystemOne(numElevators, highestFloor, 500);
            dispose();

            Thread thread = new Thread(() -> {
                system.startSimulation();
            });
            thread.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for elevators and floors.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElevatorInputer::new);
    }
}
