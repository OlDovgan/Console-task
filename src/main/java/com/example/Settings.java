package com.example;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Settings {

  public final Scanner scanner = new Scanner(System.in);

  public void numberCheck() {
    while (!scanner.hasNextInt()) {
      System.out.print("Please, enter number ");
      scanner.next();
    }
  }

  public void stringCheck() {
    while (!scanner.hasNextLine()) {
      System.out.print("Please, enter string ");
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
    System.out.println(message);
    stringCheck();
    return scanner.nextLine();
  }
}
