package org.example;

import java.util.List;

public interface ElevatorSystem {
    void startSimulation();
    void stopSimulation();
    void pickup(int floor, int direction);
    void update();
    void step();
    List<Elevator> status();
    int getUpRequests(int floor) ;
    int getDownRequests(int floor);
    int getDestRequest(int floor);
}
