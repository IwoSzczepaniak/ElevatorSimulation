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
        setLayout(new GridLayout(highestFloor+1, system.status().size() * 2));

        initElevatorLabels();
    }

    private void createJLabel(String name){
        JLabel label = new JLabel(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }

    private void initElevatorLabels() {

        createJLabel("UP");
        createJLabel("DOWN");
        createJLabel("");
        createJLabel("");


        for(int i = 0 ; i < system.status().size(); i++){
            createJLabel("E" + i);
        }

        createJLabel("DEST");


        for (int i = highestFloor - 1; i >= 0; i--) {
            createJLabel(String.valueOf(system.getUpRequests(i)));
            createJLabel(String.valueOf(system.getDownRequests(i)));

            JButton upButton = createArrowButton("^");
            final int currentFloor = i;

            upButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    system.pickup(currentFloor, 1); // Move up
                }
            });
            add(upButton);

            JButton downButton = createArrowButton("v");
            downButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    system.pickup(currentFloor, -1); // Move down
                }
            });
            add(downButton);

            for (final Elevator elevator : system.status()) {
                JLabel label = new JLabel("-");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                if (elevator.getCurrentFloor() == currentFloor) {
                    label.setText("X");
                    label.setOpaque(true);
                    label.setBackground(Color.GREEN); // Current floor indicator
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
        return button;
    }

    public void updateElevatorStatus() {
        getContentPane().removeAll();
        initElevatorLabels();
        revalidate();
        repaint();
    }
}