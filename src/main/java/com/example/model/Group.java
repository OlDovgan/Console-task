package com.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "groups")
@Data
public class Group {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "group", cascade = { CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.REFRESH},fetch = FetchType.EAGER)
  private List<Student> students;

  public Group() {
  }

  public Group(String name) {
    this.name = name;
  }
}
