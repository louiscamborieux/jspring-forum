package com.example.springboot;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity // This tells Hibernate to make a table out of this class
public class Utilisateur implements UserDetails {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String username;

  private String email;

  private String role;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String name) {
    this.username = name;
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

  private String password;

  public String getPassword() {
    return password;
  }

  public boolean isModerator () {
    return this.getRole().equals("moderator");
  }

  @OneToMany (mappedBy = "post")
  private List<Jaime> aimes;


  @OneToMany (mappedBy = "post")
  private List<JaimePas> aimentPas;

  @Override
  public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    if (isModerator()) {
      authorities.add(new SimpleGrantedAuthority("moderator"));
    }
    else {
      authorities.add(new SimpleGrantedAuthority("publisher"));
    }

    return authorities;
  }



  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
      return true;
    }

}