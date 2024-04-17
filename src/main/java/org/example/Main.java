package org.example;

public class Main {
    public static void main(String[] args) throws Exception{
        ElevatorSystem system = new SystemOne(5, 10);

        for (int i = 0; i < 700; i++) {
            system.step();
            try {
                Thread.sleep(1000); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}