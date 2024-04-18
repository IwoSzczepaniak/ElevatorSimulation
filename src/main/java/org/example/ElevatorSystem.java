package org.example;

import java.util.List;

public interface ElevatorSystem {
    void run();
    void pickup(int floor, int direction);
    void update(int elevatorId, int currentFloor, int targetFloor);
    void step();
    List<Elevator> status();
    void show();

    public int getUpRequests(int floor) ;
    public int getDownRequests(int floor);
    public int getDestRequest(int floor);

}
