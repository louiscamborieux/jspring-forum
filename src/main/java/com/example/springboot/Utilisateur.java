package com.example.springboot;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // This tells Hibernate to make a table out of this class
public class Utilisateur implements UserDetails {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String username;

  @JsonIgnore
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

  @JsonIgnore
  @Column(name ="password")
  private String password;

  public void setPassword(String password) {
    PasswordEncoder encodeur = new BCryptPasswordEncoder(12);
    this.password = encodeur.encode(password);
  }

  public String getPassword() {
    return password;
  }

  @JsonIgnore
  public boolean isModerator () {
    return this.getRole().equals("moderator");
  }

  public boolean equals(Utilisateur user2) {
    return this.id == user2.id;
  }

  @OneToMany (mappedBy = "post")
  private List<Jaime> aimes;


  @OneToMany (mappedBy = "post")
  private List<JaimePas> aimentPas;

  @Override
  @JsonIgnore
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
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
      return true;
    }

}