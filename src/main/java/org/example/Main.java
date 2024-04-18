package org.example;

public class Main {
    public static void main(String[] args) throws Exception{
        ElevatorSystem system = new SystemOne(5, 10, 500);
        system.startSimulation();
    }
}