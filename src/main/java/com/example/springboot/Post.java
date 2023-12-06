package com.example.springboot;

import java.util.List;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Post {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column (nullable = false)
  private String titre;

  @Column (nullable = false)
  private String contenu;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public String getContenu() {
    return contenu;
  }

  public void setContenu(String contenu) {
    this.contenu = contenu;
  }

  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn(name = "id_auteur", nullable = false)
  private Utilisateur auteur;

  public void setAuteur (Utilisateur auteur) {
    this.auteur = auteur;
  }


  public Utilisateur getAuteur () {
    return auteur;
  }

  @OneToMany (mappedBy = "post")
  private List<Jaime> aimes;


  @OneToMany (mappedBy = "post")
  private List<JaimePas> aimentPas;
    
}

