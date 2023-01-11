package com.example;

import java.util.Scanner;

public class Utility {

  public final Scanner scanner = new Scanner(System.in);

  public void numberCheck() {
    while (!scanner.hasNextInt()) {
      System.out.print("Please, enter number ");
      scanner.next();
    }
  }
  public void endExecution() {
    scanner.nextLine();
    System.out.println("Please, press enter to continue");
    scanner.nextLine();
  }

  public int readInt(int num) {
    numberCheck();
    int menuItem = scanner.nextInt();
    while (!(menuItem > 0 && menuItem <= num)) {
      System.out.println("Please make your choice");
      numberCheck();
      menuItem = scanner.nextInt();
    }
    return menuItem;
  }

  public int readInt(String message) {
    System.out.println(message);
    numberCheck();
    return scanner.nextInt();
  }

  public String readString(String message) {
    String pattern = "[A-Z][a-z]+\\s+[A-Z][a-z]+";
    System.out.println(message);
    String resultString = scanner.nextLine();
    while (!resultString.matches(pattern)) {
      System.out.print("Please enter name and surname of the student ");
      resultString = scanner.nextLine();
    }
    return resultString;
  }
}
