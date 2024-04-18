package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElevatorVisualization extends JFrame {
    private final int highestFloor;
    private final ElevatorSystem system;

    public ElevatorVisualization(ElevatorSystem system, int highestFloor) {
        this.system = system;
        this.highestFloor = highestFloor;

        setTitle("Elevator System Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new GridLayout(highestFloor + 2, system.status().size() * 2));

        initElevatorLabels();
    }

    private void createJLabel(String name) {
        JLabel label = new JLabel(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }

    private void initElevatorLabels() {
        for (int i = 0; i < system.status().size() + 4; i++) {
            createJLabel("");
        }

        JButton exitButton = new JButton("X");
        exitButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        exitButton.setForeground(Color.WHITE);
        exitButton.setMargin(new Insets(0, 0, 0, 0));
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED); // Set button background color to red

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                system.stopSimulation();
                dispose();
            }
        });
        add(exitButton);

        createJLabel("UP");
        createJLabel("DOWN");
        createJLabel("");
        createJLabel("");

        for (int i = 0; i < system.status().size(); i++) {
            createJLabel("E" + i);
        }

        createJLabel("DEST");

        for (int i = highestFloor - 1; i >= 0; i--) {
            createJLabel(String.valueOf(system.getUpRequests(i)));
            createJLabel(String.valueOf(system.getDownRequests(i)));

            final int currentFloor = i;

            if (i != highestFloor - 1) {
                JButton upButton = createArrowButton("^");
                upButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        system.pickup(currentFloor, 1); // Move up
                    }
                });
                add(upButton);
            } else {
                createJLabel(" ");
            }

            if (i != 0) {
                JButton downButton = createArrowButton("v");
                downButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        system.pickup(currentFloor, -1); // Move down
                    }
                });
                add(downButton);
            } else {
                createJLabel(" ");
            }

            for (final Elevator elevator : system.status()) {
                JLabel label = new JLabel("-"); // Square sign
                label.setHorizontalAlignment(SwingConstants.CENTER);
                if (elevator.getCurrentFloor() == currentFloor) {
                    label.setText("\u25A0");
                    label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                    label.setForeground(Color.GRAY); // Set text color to white
                    label.setOpaque(true);
                }
                add(label);
            }
            createJLabel(String.valueOf(system.getDestRequest(i)));
        }
    }

    private JButton createArrowButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);

        return button;
    }

    public void updateElevatorStatus() {
        getContentPane().removeAll();
        initElevatorLabels();
        revalidate();
        repaint();
    }
}
