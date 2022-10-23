package com.example;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.example.menu.MainMenu;
import com.example.menu.Menu;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class MainMenuTest {
  @Test
    void addMenuItem_ShouldAddMenuItemAndRunMenuItem() throws SQLException {
    System.setIn(new ByteArrayInputStream(String.format("1%n").getBytes()));
    MainMenu menu = new MainMenu(new Settings());
    Menu test = mock(Menu.class);
    Mockito.when(test.getItemName()).thenReturn("Test menu");
    menu.addMenuItem(test);
    doNothing().when(test).executeMenu();
    menu.executeMenu();
    verify(test, times(1)).executeMenu();
  }
  @Test
  void executeMenu() throws SQLException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    String separator= System.lineSeparator();
    System.setOut(new PrintStream(output));
    System.setIn(new ByteArrayInputStream(String.format("1%n").getBytes()));
    MainMenu menu = new MainMenu(new Settings());
    Menu test = mock(Menu.class);
    Mockito.when(test.getItemName()).thenReturn("Test menu");
    menu.addMenuItem(test);
    doNothing().when(test).executeMenu();
    menu.executeMenu();
    String expect = "1. Test menu"+separator
                   +"Please make your choice"+separator;
    Assertions.assertEquals(expect,output.toString());
  }
}
