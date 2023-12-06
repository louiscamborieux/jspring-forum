package com.example;

import com.example.springboot.Utilisateur;

public class PostRequest {
    private String titre;
    private String contenu;
    private int auteurID;

    public PostRequest(String titre, String contenu, int auteurID) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteurID = auteurID;
    }

    // Getters and setters
    public String getTitre() {
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

    public int getAuteur() {
        return auteurID;
    }

    public void setAuteur(int auteurID) {
        this.auteurID = auteurID;
    }
}
