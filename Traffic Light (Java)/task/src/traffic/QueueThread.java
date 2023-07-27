package traffic;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueThread extends Thread {

    private int roads;

    private int interval;

    public enum State {
        NOT_STARTED, MENU, SYSTEM
    }

    State systemState = State.NOT_STARTED;

    private int secondsSinceCreation = 0;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public QueueThread() {
        this.setRoads();
        this.setInterval();
        this.setName("QueueThread");
        this.start();
    }

    @Override
    public void run() {
        running.set(true);
        setSystemState(State.MENU);
        Timer timer = new Timer();
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                if (!running.get()) {
                    timer.cancel();
                    timer.purge();
                    return;
                }
                secondsSinceCreation++;
                if (systemState == State.SYSTEM) {
                    printState();
                }
            }
        };
        timer.scheduleAtFixedRate(timertask, 1000, 1000);
        while (running.get()) {

        }
    }

    public void end() {
        running.set(false);
    }

    void printState() {
        System.out.printf("! %ds. have passed since system startup !\n", secondsSinceCreation);
        System.out.printf("! Number of roads: %d !\n", roads);
        System.out.printf("! Interval: %d !\n", interval);
        System.out.println("! Press \"Enter\" to open menu !");
    }

    public void setSystemState(State systemState) {
        this.systemState = systemState;
    }

    public void addRoad() {
        this.roads++;
    }

    public void deleteRoad() {
        this.roads--;
    }

    void setRoads() {
        System.out.println("Input the number of roads:");
        Scanner scanner = new Scanner(System.in);
        int input;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input > 0) {
                    this.roads = input;
                    return;
                }
            }
            scanner.nextLine();
            System.out.print("Error! Incorrect input. Try again: ");
        }
    }
    void setInterval() {
        System.out.println("Input the interval:");
        Scanner scanner = new Scanner(System.in);
        int input;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input > 0) {
                    this.interval = input;
                    return;
                }
            }
            scanner.nextLine();
            System.out.print("Error! Incorrect input. Try again: ");
        }
    }
}
