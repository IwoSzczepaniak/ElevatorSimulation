package org.example;

import java.util.Random;

public class Elevator {
    private final int elevatorId;
    private final  int highestFloor;
    private final int[] upRequest;
    private final int[] downRequest;
    private final int[] passengerRequest;
    private final Random rand = new Random(0);
    private int direction;
    private int currentFloor;


    public Elevator(int highestFloor, int elevatorId) {
        this.elevatorId = elevatorId;
        this.highestFloor = highestFloor;
        this.upRequest = new int[highestFloor];
        this.downRequest = new int[highestFloor];
        this.passengerRequest = new int[highestFloor];
        this.direction = 0;
        this.currentFloor = 0;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getPassengersAmount(int floor){
        return passengerRequest[floor];
    }

    public int getCurrentLoad(){
        int sum = 0;
        for(int i = 0; i < highestFloor; i++){
            if(upRequest[i] > 0) sum+=1;
            if(downRequest[i] > 0) sum+=1;
            if(passengerRequest[i]>0) sum+=1;
        }
        return sum;
    }

    public void addFloorToUp(int floor){
        upRequest[floor]++;
    }

    public void addFloorToDown(int floor){
        downRequest[floor]++;
    }

    public int move() {
        if(!upRequestsExist() && !downRequestsExist() && currentFloor > 0){
            currentFloor--;
            return currentFloor;
        }

        boolean upFlag = false;
        boolean downFlag = false;
        // Check if there are any requests in the current direction
        if (direction == 1) { // Moving up
            if (upRequestsExist()) {
                upFlag = true;
            }
            else if (downRequestsExist()){
                direction = -1;
                downFlag = true;
            }
            else{
                direction = 0;
            }
        }
        else if (direction == -1) { // Moving down
            if (downRequestsExist()) {
                downFlag = true;
            }
            else if (upRequestsExist()){
                direction = 1;
                upFlag = true;
            }
            else{
                direction = 0;
            }
        }

        else if (direction == 0){
            if(downRequestsExist() || upRequestsExist()) {
                upFlag = true;
                if (upRequest[currentFloor] > 0) {
                    direction = 1;
                } else if (downRequest[currentFloor] > 0) {
                    direction = -1;
                    downFlag = true;
                    upFlag = false;
                }
            }
            else {
                downFlag = true;
            }
        }

        if (upFlag && currentFloor < highestFloor-1) currentFloor++;
        else if(downFlag && currentFloor > 0) currentFloor--;

        while(upFlag && upRequest[currentFloor] > 0){
            passengerRequest[rand.nextInt(currentFloor+1, highestFloor)]++; // Random destiation
            upRequest[currentFloor]--;
        }

        while(downFlag && downRequest[currentFloor] > 0){
            passengerRequest[rand.nextInt(0, currentFloor)]++; // Random destiation
            downRequest[currentFloor]--;
        }

        while(passengerRequest[currentFloor] > 0){
            passengerRequest[currentFloor]--;
        }

        return currentFloor;
    }

    private boolean upRequestsExist() {
        for (int i = currentFloor + 1; i < highestFloor; i++) {
            if (upRequest[i] > 0 || passengerRequest[i] > 0 || downRequest[i] > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean downRequestsExist() {
        for (int i = currentFloor - 1; i >= 0; i--) {
            if (downRequest[i] > 0 || passengerRequest[i] > 0 || upRequest[i] > 0) {
                return true;
            }
        }
        return false;
    }
}
