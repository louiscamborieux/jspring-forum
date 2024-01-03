package com.example.springboot;

import java.time.LocalDate;

public class PostRestreint {
    private Utilisateur auteur;
    private LocalDate datePost;
    private String contenu;

     public Utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }
    
    public LocalDate getDatePost() {
        return datePost;
    }

    public void setDatePost(LocalDate datePost) {
        this.datePost = datePost;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
}
