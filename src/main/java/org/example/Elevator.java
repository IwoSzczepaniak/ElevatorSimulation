package org.example;

import java.util.Random;

public class Elevator {
    private final int elevatorId;
    private int currentFloor;
    private int targetFloor;
    private final  int highestFloor;
    private int[] upRequest;
    private int[] downRequest;
    private int[] passengerRequest;
    private final Random rand = new Random(0);

    public Elevator(int highestFloor, int elevatorId, int currentFloor, int targetFloor) {
        this.elevatorId = elevatorId;
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
        this.highestFloor = highestFloor;
        this.upRequest = new int[highestFloor];
        this.downRequest = new int[highestFloor];
        this.passengerRequest = new int[highestFloor];
    }

    private int searchNewTargetUp(){
        /* this shall work like LOOK algorithm */
        for (int i = highestFloor - 1; i > -1; i--) {
            if (upRequest[i] > 0 || passengerRequest[i] > 0) {
                return i;
            }
        }
        return 0;
    }

    private int searchNewTargetDown(){
        /* this shall work like LOOK algorithm */
        for(int i = 0; i < highestFloor; i++){
            if(downRequest[i] > 0 || passengerRequest[i] > 0){
                return i;
            }
        }
        return 0;
    }

    public int move(){
        int priorFloor = currentFloor;
        if(currentFloor < targetFloor){
            currentFloor++;
            if(currentFloor == targetFloor || currentFloor == highestFloor - 1){
                int newTargetUp = searchNewTargetUp();
                if (newTargetUp == targetFloor) targetFloor = searchNewTargetDown();
                else targetFloor = newTargetUp;
            }
        }
        else if (currentFloor > targetFloor){
            currentFloor--;
            if(currentFloor == targetFloor || currentFloor == 0){
                int newTargetDown = searchNewTargetDown();
                if (newTargetDown == targetFloor) targetFloor = searchNewTargetUp();
                else targetFloor = newTargetDown;
            }
        }

        while (upRequest[currentFloor] > 0) { // Up direction
            addPassenger(rand.nextInt(currentFloor, highestFloor)); // Random destiation
            upRequest[currentFloor]--;
        }
        while (downRequest[currentFloor] > 0) { // Down direction
            addPassenger(rand.nextInt(0, priorFloor)); // Random destiation
            downRequest[currentFloor]--;
        }

        if (passengerRequest[currentFloor] > 0) passengerRequest[currentFloor] = 0;

        return currentFloor;
    }

    public int getDestRequestAmount(int floor){
        return passengerRequest[floor];
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    private void updateTargetFloor(int floor){
        /* this shall work like LOOK algorithm
           it should update only if 1) going in the same direction 2) floor is further than targerFloor */
        boolean current_dir = currentFloor >= targetFloor;
        boolean wanna_dir = floor >= targetFloor;
        if (current_dir == wanna_dir && Math.abs(currentFloor - floor) > Math.abs(currentFloor - targetFloor)){
            targetFloor = floor;
        }
    }

    public void addFloorToUp(int floor){
        upRequest[floor]++;
        targetFloor = searchNewTargetUp();
    }

    public void addFloorToDown(int floor){
        downRequest[floor]++;
        targetFloor = searchNewTargetDown();
    }

    public void addPassenger(int floor){
        passengerRequest[floor]++;
        updateTargetFloor(floor);
    }


}
