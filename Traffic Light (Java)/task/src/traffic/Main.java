package traffic;

import java.util.Scanner;

public class Main {

  public static void main(String[] args){
    printWelcomeMessage();
    TrafficSystem trafficSystem = new TrafficSystem();
    printMenu();
    Scanner scanner = new Scanner(System.in);
    int input = scanner.nextInt();
    String message;

    while (input != 0) {
      message = trafficSystem.treatAction(input);
      System.out.println(message);
      printMenu();
      input = scanner.nextInt();
    }
    System.out.println("Bye!");
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
