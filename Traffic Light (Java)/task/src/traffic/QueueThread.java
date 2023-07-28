package traffic;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueThread extends Thread {

    private LinkedList<String> roads;

    int activeRoadIndex = 0;

    int roadsCapacity;

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
                    clearCommand();
                    printState();
                }
                if (roads.size() != 0 && interval - secondsSinceCreation % interval == 1) {
                    activeRoadIndex = (activeRoadIndex + 1) % roads.size();
                }
            }
        };
        timer.scheduleAtFixedRate(timertask, 0, 1000);
        while (running.get()) {

        }
    }

    public void end() {
        running.set(false);
    }

    void printState() {
        System.out.printf("! %ds. have passed since system startup !\n", secondsSinceCreation);
        System.out.printf("! Number of roads: %d !\n", roadsCapacity);
        System.out.printf("! Interval: %d !\n", interval);
        for (String roadName: roads) {
            int timer = interval - secondsSinceCreation % interval;
            int roadIndex = roads.indexOf(roadName);
            String status = roadIndex == activeRoadIndex ? "open" : "closed";
            String color = roadIndex == activeRoadIndex ? "\u001B[32m" : "\u001B[31m";
            if (roadIndex < activeRoadIndex) {
                timer += interval * (roads.size() - 1 - activeRoadIndex + roadIndex);
            } else if (roadIndex > activeRoadIndex) {
                timer += interval * (roadIndex - activeRoadIndex - 1);
            }
            System.out.printf("Road \"%s\" will be " + color + "%s for %ds.\n" + "\u001B[0m", roadName, status, timer);
        }
        System.out.println("! Press \"Enter\" to open menu !");
    }

    public void setSystemState(State systemState) {
        this.systemState = systemState;
    }

    public int getRoadSize() {
        return this.roads.size();
    }

    public void addRoad() {
        System.out.println("Input road name:");
        Scanner scanner = new Scanner(System.in);
        String roadName;
        roadName = scanner.nextLine();
        if (this.roads.size() < this.roadsCapacity) {
            this.roads.add(roadName);
            System.out.printf("%s added!\n", roadName);
        } else {
            System.out.println("Queue is full");
        }
    }

    public void deleteRoad() {
        if (this.roads.size() > 0) {
            String roadName = this.roads.poll();
            System.out.printf("%s deleted!\n", roadName);
        } else {
            System.out.println("Queue is empty");
        }
    }

    void setRoads() {
        System.out.println("Input the number of roads:");
        Scanner scanner = new Scanner(System.in);
        int input;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input > 0) {
                    this.roads = new LinkedList<>();
                    this.roadsCapacity = input;
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

    static void clearCommand() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        }
        catch (InterruptedException | IOException ignored) {}
    }
}
