package com.example.springboot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.springboot.Utilisateur;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface JaimeRepository extends CrudRepository<Jaime, Integer> {
    Optional<Jaime> findByUtilisateurAndPost(Utilisateur utilisateur, Post post);
}