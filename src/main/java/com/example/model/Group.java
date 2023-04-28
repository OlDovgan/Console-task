package com.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "groups")
@Data
public class Group {

  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "sequence_group", sequenceName = "groups_seq", allocationSize = 10)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_group")
  private int id;
  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.DETACH,
      CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, orphanRemoval = true)

  private List<Student> students;

  public Group() {
  }

  public Group(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      System.err.println("if (obj == null || obj.getClass() != this.getClass())");
      return false;
    }

    Group group = (Group) obj;
    if (!this.name.equals(group.getName())) {
      System.err.println("if (!this.name.equals(group.getName()))");
      return false;
    }
    return this.id == group.getId();
  }

  @Override
  public int hashCode() {
    int result = name == null ? 0 : name.hashCode();
    result = 31 * result + id;
    return result;
  }
}
