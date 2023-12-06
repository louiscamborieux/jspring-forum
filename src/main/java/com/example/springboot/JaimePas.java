package com.example.springboot;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class JaimePas {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn(name = "id_utilisateur", nullable = false)
  private Utilisateur utilisateur;

  public void setUtilisateur (Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }

  public Utilisateur getUtilisateur () {
    return utilisateur;
  }

  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn(name = "id_post", nullable = false)
  private Post post;

  public void setPost (Post post) {
    this.post = post;
  }

  public Post getPost () {
    return post;
  }

}