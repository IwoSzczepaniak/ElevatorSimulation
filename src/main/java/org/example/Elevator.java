package org.example;

import java.util.Random;

public class Elevator {
    private final int elevatorId;
    private int currentFloor;
    private int targetFloor;
    private final  int highestFloor;
    private int[] destResquest;
    private int[] destWaiting;
    private final Random rand = new Random(0);



    public Elevator(int highestFloor, int elevatorId, int currentFloor, int targetFloor) {
        this.elevatorId = elevatorId;
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
        this.highestFloor = highestFloor;
        this.destResquest = new int[highestFloor];
    }

    private void searchNewTargetUp(){
        /* this shall work like LOOK algorithm */
        for (int i = highestFloor - 1; i > -1; i--) {
            if (destResquest[i] > 0) {
                targetFloor = i;
                break;
            }
        }
    }

    private void searchNewTargetDown(){
        /* this shall work like LOOK algorithm */

        for(int i = 0; i < highestFloor; i++){
            if(destResquest[i] > 0){
                targetFloor = i;
                break;
            }
        }
    }

    public int move(){
        int priorFloor = currentFloor;
        if(currentFloor < targetFloor){
            currentFloor++;
            if(currentFloor == targetFloor || currentFloor == highestFloor){
                searchNewTargetDown();
            }
        }
        else if (currentFloor > targetFloor){
            currentFloor--;
            if(currentFloor == targetFloor || currentFloor == 0){
                searchNewTargetUp();
            }
        }

        while (destResquest[currentFloor] > 0){
            // TODO direction should not depend on the motion of the elevator but on the button
            if (priorFloor > currentFloor) { // Up direction
                addPassengerToDest(rand.nextInt(priorFloor+1, highestFloor)); // Random destiation
            } else if (priorFloor < currentFloor) { // Down direction
                addPassengerToDest(rand.nextInt(0, priorFloor)); // Random destiation
            }
            destResquest[currentFloor]--;
        }

        destResquest[currentFloor] = 0;


        searchNewTargetUp();

        return currentFloor;
    }

    public int getDestRequestAmount(int floor){
        return Math.abs(destResquest[floor]);
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

    public void addFloorToDest(int floor){
        destResquest[floor]++;
        if (Math.abs(currentFloor - floor) > Math.abs(currentFloor - targetFloor)){
            targetFloor = floor;
        }
    }

    public void addPassengerToDest(int floor){
        destResquest[floor]--;
        if (Math.abs(currentFloor - floor) > Math.abs(currentFloor - targetFloor)){
            targetFloor = floor;
        }
    }

}
