package org.example;

import java.util.ArrayList;
import java.util.List;

public class SystemOne implements ElevatorSystem{
    private final List<Elevator> elevators;
    private final int highestFloor;
    private final int[] upRequests;
    private final int[] downRequests;

    private final ElevatorVisualization visualization;
    private Thread[] visualizationThread;
    private Thread elevatorThread;
    private int simStep;
    private boolean running;



    public SystemOne(int elevatorNumber, int highestFloor, int simStep) throws Exception {
        if (elevatorNumber > 16) throw new Exception("Too big elevator number!");
        this.elevators = new ArrayList<>();
        this.highestFloor = highestFloor;
        addElevatorsToList(elevatorNumber);
        this.downRequests = new int[highestFloor];
        this.upRequests = new int[highestFloor];
        this.visualization = new ElevatorVisualization(this, highestFloor);
        this.visualization.setVisible(true);
        this.simStep = simStep;
        this.running = true;
    }

    void addElevatorsToList(int n){
        for(int i = 0; i < n; i++) {
            this.elevators.add(new Elevator(highestFloor, i));
        }
    }

    @Override
    public void run(){
        while(running) {
            step();
            try {
                Thread.sleep(simStep); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRun(){
        running = false;
    }

    @Override
    public int getUpRequests(int floor) {
            return upRequests[floor];
    }

    @Override
    public int getDownRequests(int floor) {
        return downRequests[floor];
    }

    @Override
    public int getDestRequest(int floor){
        int res = 0;
        for(Elevator elevator : elevators){
            res += elevator.getPassengersAmount(floor);
        }
        return res;
    }

    private int findHandlingElevator(int new_floor){
        // TODO - resolve weird
        int min_load = Integer.MAX_VALUE;
        int min_i = 0;
        for(int i = 0; i < elevators.size(); i++){
            Elevator elevator = elevators.get(i);
            int new_load = elevator.getCurrentLoad();
            if (min_load > new_load){
                min_load = new_load;
                min_i = i;
            }
        }
        return min_i;
    }

    @Override
    public void pickup(int floor, int direction) {
        int elevatorId = findHandlingElevator(floor);

        if (direction == 1) { // Up direction
            upRequests[floor]++;
            elevators.get(elevatorId).addFloorToUp(floor);

        } else if (direction == -1) { // Down direction
            downRequests[floor]++;
            elevators.get(elevatorId).addFloorToDown(floor);
        }

    }

    @Override
    public void update(int elevatorId, int currentFloor, int targetFloor) {
        Elevator elevator = elevators.get(elevatorId);
//        elevator.setCurrentFloor(currentFloor);
//        elevator.setTargetFloor(targetFloor);
    }

    @Override
    public void step() {
        for(Elevator elevator : elevators){
            int prev_floor = elevator.getCurrentFloor();
            int floor = elevator.move();
            if (prev_floor > floor) downRequests[prev_floor] = 0;
            else if (prev_floor < floor) upRequests[prev_floor] = 0;
        }
        show();
    }

    @Override
    public List<Elevator> status() {
        return elevators;
    }

    @Override
    public void show() {
        visualization.updateElevatorStatus();
    }

}
