package com.example.springboot;

import java.util.List;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity // This tells Hibernate to make a table out of this class
public class Post {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column (nullable = false)
  private String titre;

  @Column (nullable = false)
  private String contenu;

  @Column (nullable = false, name="date_post")
  private LocalDate datePost = LocalDate.now();

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

  @ManyToOne ()
  @JoinColumn(name = "id_auteur", nullable = false)
  private Utilisateur auteur;

  public void setAuteur (Utilisateur auteur) {
    this.auteur = auteur;
  }

  public LocalDate getdatePost () {
    return datePost;
  }


  public Utilisateur getAuteur () {
    return auteur;
  }

  @OneToMany (mappedBy = "post",  cascade = CascadeType.REMOVE)

  private List<Jaime> aimes;

  @JsonIgnore
  public List<Jaime> getAimes() {
    return aimes;
}

@JsonProperty("Nombre de j'aime")
public int nbAime () {
  return aimes != null ? aimes.size() : 0;
}

@JsonProperty("Nombre de je n'aime pas")
public int nbJaimePas () {
  return aimentPas != null ? aimentPas.size() : 0;
}


  @OneToMany (mappedBy = "post",  cascade = CascadeType.REMOVE)
  @JsonIgnore
  private List<JaimePas> aimentPas;

  public List<JaimePas> getAimentPas() {
    return aimentPas;
}
    
}

