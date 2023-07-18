package traffic;

import traffic.TrafficSystem;
import java.io.IOException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {
    printWelcomeMessage();
    TrafficSystem trafficSystem = new TrafficSystem();
    int input = getInput();
    String message;

    while (input != 0) {
      message = trafficSystem.treatAction(input);
      System.out.println(message);
      System.in.read();
      input = getInput();
    }
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
    catch (InterruptedException | IOException e) {}
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
