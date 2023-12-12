package com.example.springboot;


import java.util.List;


public class JwtDTO {
    private final String token;
    private final String type = "Bearer";
    private final String username;
    private final List<String> roles;


 // Constructeur
    public JwtDTO(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    // Méthodes getters
    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}