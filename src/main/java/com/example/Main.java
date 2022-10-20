package com.example;


import com.example.dao.StudentDao;
import com.example.menu.AddNewStudent;
import com.example.menu.AddStudentToCourse;
import com.example.menu.CreateData;
import com.example.menu.DeleteStudent;
import com.example.menu.Exit;
import com.example.menu.FindAllGroups;
import com.example.menu.FindStudentsWithCourse;
import com.example.menu.MainMenu;
import com.example.menu.Next;
import com.example.menu.RemoveStudentFromCourse;

public class Main {

  public static void main(String... args) {
    Settings settings = new Settings();
    MainMenu firstMenu = new MainMenu(settings);
    MainMenu secondMenu = new MainMenu(settings);
    JDBC jdbc = new JDBC(settings.properties("DB.properties"));
    StudentDao studentDao = new StudentDao();
    Request request = new Request(jdbc, studentDao);
    firstMenu(firstMenu,jdbc);
    firstMenu.executeMenu();
    secondMenu(secondMenu, settings, request, jdbc);
    while (true) {
      secondMenu.executeMenu();
    }

  }

  public static void firstMenu(MainMenu menu, JDBC jdbc) {
    menu.addMenuItem(new CreateData(jdbc));
    menu.addMenuItem(new Next());
  }

  public static void secondMenu(MainMenu menu, Settings settings, Request request, JDBC jdbc) {
    menu.addMenuItem(new FindAllGroups(settings,request));
    menu.addMenuItem(new FindStudentsWithCourse(settings,request));
    menu.addMenuItem(new AddNewStudent(settings,request ));
    menu.addMenuItem(new DeleteStudent(settings, request));
    menu.addMenuItem(new AddStudentToCourse(settings, request));
    menu.addMenuItem(new RemoveStudentFromCourse(request));
    menu.addMenuItem(new Exit());

  }
}
