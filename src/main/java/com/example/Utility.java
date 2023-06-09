package com.example;

import com.example.model.Student;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

  private final Logger logger
      = LoggerFactory.getLogger(Utility.class);
  final Scanner scanner = new Scanner(System.in);

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
      System.out.print("Please enter name and surname of the student ");
      resultString = scanner.nextLine();
    }
    logger.debug("Method readInt- return value is " + resultString);
    return resultString;
  }
  public  int getStudentId (List<Student> studentList) {
    int studId = 0;
    HashSet<Integer> set = new HashSet<>();
    for (Student student : studentList) {
      set.add(student.getId());
    }
    while (!set.contains(studId)) {
      studId = readInt("Please, select a student ID to delete the course ");
    }
    return studId;
  }

}
