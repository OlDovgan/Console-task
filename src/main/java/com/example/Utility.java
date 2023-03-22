package com.example;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

  private final Logger logger
      = LoggerFactory.getLogger(Utility.class);
  final Scanner scanner = new Scanner(System.in);

  public void numberCheck() {
    while (!scanner.hasNextInt()) {
      logger.debug("Method numberCheck() - invalid data type");
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
      logger.debug("Method readInt(int num) - number out of range."
          + " The range should be from 0 to " + num + ". The entered value was " + menuItem + ".");
      System.out.println("Please make your choice");
      numberCheck();
      menuItem = scanner.nextInt();

    }
    logger.debug("Method readInt(int num)  - return value is " + menuItem);
    return menuItem;
  }

  public int readInt(String message) {
    System.out.println(message);
    numberCheck();
    int returnInt = scanner.nextInt();
    logger.debug("Method readInt(String message)  - return value is " + returnInt);
    return returnInt;
  }

  public String readString(String message) {
    String pattern = "[A-Z][a-z]+\\s+[A-Z][a-z]+";
    System.out.println(message);
    String resultString = scanner.nextLine();
    while (!resultString.matches(pattern)) {
      logger.debug("Method readString - the entered string does not match the format."
          + "Entered value " + resultString);
      System.out.print("Please enter name and surname of the student ");
      resultString = scanner.nextLine();
    }
    logger.debug("Method readInt- return value is " + resultString);
    return resultString;
  }
}
