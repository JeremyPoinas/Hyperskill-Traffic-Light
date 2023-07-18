package traffic;

import java.util.Scanner;

public class TrafficSystem {
    private int roads;
    private int interval;
    public TrafficSystem() {
        this.roads = setRoads();
        this.interval = setInterval();
    }
    int setRoads() {
        System.out.println("Input the number of roads:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    int setInterval() {
        System.out.println("Input the interval:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    String treatAction(int input) {
        String message = null;
        switch (input) {
            case 1 -> {
                this.roads++;
                message = "Road added";
            }
            case 2 -> {
                this.roads--;
                message = "Road deleted";
            }
            case 3 -> {
                message = "System opened";
            }
        }
        return message;
    }

}
