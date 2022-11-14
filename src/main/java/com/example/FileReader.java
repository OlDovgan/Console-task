package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class FileReader {

  public List<String> readFile(String fileName)
      throws FileNotFoundException, URISyntaxException {
    URL resource = this.getClass().getClassLoader().getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("File not found!!!");
    } else {
      List<String> list = new ArrayList<>();
      try (Scanner obj = new Scanner(new File(resource.toURI().getPath()))) {
        while (obj.hasNextLine()) {
          list.add(obj.nextLine());
        }
        return list;
      }
    }
  }

}
