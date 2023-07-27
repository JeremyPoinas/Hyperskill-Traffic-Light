package traffic;

import java.io.IOException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {

    printWelcomeMessage();
    QueueThread queueThread = new QueueThread();

    int input = getInput();

    while (input != 0) {
      switch (input) {
        case 1 -> {
          queueThread.addRoad();
          System.out.println("Road added");
          System.in.read();
        }
        case 2 -> {
          queueThread.deleteRoad();
          System.out.println("Road deleted");
          System.in.read();
        }
        case 3 -> {
          queueThread.setSystemState(QueueThread.State.SYSTEM);
          System.in.read();
          queueThread.setSystemState(QueueThread.State.MENU);
        }
      }
      input = getInput();
    }
    queueThread.end();
    queueThread.join();
    System.out.println("Bye!");
  }

  static int getInput() throws IOException {
    printMenu();
    Scanner scanner = new Scanner(System.in);
    int input;
    while (scanner.hasNext()) {
      if (scanner.hasNextInt()) {
        input = scanner.nextInt();
        if (input >= 0 && input <= 3) {
          scanner.nextLine();
          return input;
        }
      }
      System.out.println("Incorrect option");
      scanner.nextLine();
      System.in.read();
      clearCommand();
      printMenu();
    }
    return 0;
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

  static void printMenu() {
    System.out.println("""
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit""");
  }

  static void printWelcomeMessage() {
    System.out.println("Welcome to the traffic management system!");
  }
}
