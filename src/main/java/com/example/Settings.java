package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Settings {


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

  public final Properties properties(String nameFile) {

    String rootPath = Objects.requireNonNull(
        Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(rootPath + nameFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return properties;
  }
}
