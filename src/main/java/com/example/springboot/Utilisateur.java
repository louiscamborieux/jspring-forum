package com.example.springboot;

import javax.persistence.*;
import java.util.List;

import org.hibernate.mapping.Collection;

@Entity // This tells Hibernate to make a table out of this class
public class Utilisateur {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String name;

  private String email;

  private String role;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setRole(String role) {
    this.role = role;
  }

    public String getRole() {
    return role; 
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @OneToMany (mappedBy = "auteur")
  private List<Post> posts;
}