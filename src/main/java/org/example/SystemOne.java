package org.example;

import java.util.ArrayList;
import java.util.List;

public class SystemOne implements ElevatorSystem{
    private List<Elevator> elevators;
    private final int elevatorNumber;
    private final int highestFloor;
    private final int[] upRequests;
    private final int[] downRequests;


    private final ElevatorVisualization visualization;


    public SystemOne(int elevatorNumber, int highestFloor) throws Exception {
        if (elevatorNumber > 16) throw new Exception("Too big elevator number!");
        this.elevatorNumber = elevatorNumber - 1;
        this.elevators = new ArrayList<>();
        this.highestFloor = highestFloor;
        addElevatorsToList(this.elevatorNumber);
        this.downRequests = new int[highestFloor];
        this.upRequests = new int[highestFloor];
        this.visualization = new ElevatorVisualization(this, 10);
        this.visualization.setVisible(true);
    }

    void addElevatorsToList(int n){
        for(int i = 0; i <= n; i++) {
            this.elevators.add(new Elevator(highestFloor, i, 0, 0));
        }
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
            res += elevator.getDestRequestAmount(floor);
        }
        return res;
    }

    private int findClosestElevator(int new_floor){
        // TODO - some error - it always returns 0
        /* This method is calculating the distance metrics to choose the best elevator for LOOK algorithm*/
        int min_dist = 0;
        int min_i = 0;

        for(int i = 0; i < elevators.size(); i++){
            Elevator elevator = elevators.get(i);
            boolean current_dir = elevator.getTargetFloor() >= elevator.getCurrentFloor();
            boolean new_dir = new_floor >= elevator.getCurrentFloor();
            if (new_dir == current_dir){
                int dist = Math.abs(new_floor - elevator.getCurrentFloor());
                if (dist < min_dist) {
                    min_dist = dist;
                    min_i = i;
                }
            }
            else{
                int dist = 2*Math.abs(elevator.getTargetFloor() - elevator.getCurrentFloor()) + Math.abs(new_floor - elevator.getCurrentFloor());
                if (dist < min_dist) {
                    min_dist = dist;
                    min_i = i;
                }
            }
        }

//        System.out.println(min_dist + " " + min_i);
        return min_i;
    }

    @Override
    public void pickup(int floor, int direction) {
        int elevatorId = findClosestElevator(floor);

        if (direction == 1) { // Up direction
            upRequests[floor]++;
            elevators.get(elevatorId).addFloorToUp(floor);

        } else if (direction == -1) { // Down direction
            downRequests[floor]++;
            elevators.get(elevatorId).addFloorToDown(floor);
        }

        // choose the closest elevator
        // add request to the chosen elevator
//        if (direction == 1) { // Up direction
//            elevators.get(elevatorId).addFloorToDest(rand.nextInt(floor+1, highestFloor)); // Random destiation
//        } else if (direction == -1) { // Down direction
//            elevators.get(elevatorId).addFloorToDest(rand.nextInt(0, floor)); // Random destiation
//        }


    }

    @Override
    public void update(int elevatorId, int currentFloor, int targetFloor) {
        Elevator elevator = elevators.get(elevatorId);
        elevator.setCurrentFloor(currentFloor);
        elevator.setTargetFloor(targetFloor);
    }

    @Override
    public void step() {
        for(Elevator elevator : elevators){
            int prev_floor = elevator.getCurrentFloor();
            int floor = elevator.move();
            if (prev_floor > floor) downRequests[prev_floor] = 0;
            else if (prev_floor < floor) upRequests[prev_floor] = 0;
        }
        visualization.updateElevatorStatus();
    }

    @Override
    public List<Elevator> status() {
        return elevators;
    }

    @Override
    public void show() {
        for(Elevator elevator : elevators){
            for(int i  = highestFloor-1; i > -1; i--){
                if (elevator.getCurrentFloor() == i) System.out.print('x');
                else System.out.print('-');
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

}
