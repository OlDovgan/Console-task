package com.example.menu;

import java.sql.SQLException;

public interface Menu {

  String getItemName();

  void executeMenu() throws SQLException;

}
