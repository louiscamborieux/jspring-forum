package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.PostRequest;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path="/post")
public class JaimeController {
        
    @Autowired
    private JaimePasRepository jaimePasRepository;
    
    @Autowired
    private JaimeRepository repository;
    
    @Autowired  // Ajout de l'annotation Autowired ici
    private PostRepository postRepository;

    @Autowired  // Ajout de l'annotation Autowired ici
    private UserRepository utilisateurRepository;

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> createJaime(@PathVariable Integer postId, @RequestBody PostRequest postRequest) {
        Optional<Post> opPost = postRepository.findById(postId);

        if (!opPost.isPresent()) {
            return new ResponseEntity<>("Post non trouvé",HttpStatus.NOT_FOUND);
        }

        Optional<Utilisateur> opUser = utilisateurRepository.findById(postRequest.getAuteur());
        if (!opUser.isPresent()) {
            return new ResponseEntity<>("utilisateur non enregistré",HttpStatus.NOT_FOUND);
        }
        Utilisateur user = opUser.get();
        Post post = opPost.get();

        if (user == post.getAuteur()) {
            return new ResponseEntity<>("Vous ne pouvez pas aimer vos propres posts",HttpStatus.FORBIDDEN);
        }

        Optional<JaimePas> opJaimePas = jaimePasRepository.findByUtilisateurAndPost(user, post);
        if (opJaimePas.isPresent()) {
            jaimePasRepository.delete(opJaimePas.get());
        }

        Optional<Jaime> opJaime = repository.findByUtilisateurAndPost(user, post);
        if (!opJaime.isPresent()) {
            Jaime reaction = new Jaime();
            reaction.setPost(post);
            reaction.setUtilisateur(user);
            repository.save(reaction);
            return new ResponseEntity<>("Aimé",HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("vous avez déjà aimé ce post",HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity <?> getWhoLikedPost(@PathVariable Integer postId, @RequestBody(required= false) PostRequest postRequest) {
        if (postRequest == null) {
            throw new IllegalArgumentException("Echec de l'authentification, merci d'envoyer votre id dans le corps de la requete.");
        }

        Optional<Utilisateur> opUser = utilisateurRepository.findById(postRequest.getAuteur());
        if (!opUser.isPresent()) {
            return new ResponseEntity<>("Echec de l'authentification, veuillez entrez un id utilisateur valide.",HttpStatus.NOT_FOUND);
        }

        Utilisateur user = opUser.get();
        if (!user.isModerator()) {
                       return new ResponseEntity<>("Vous n'avez pas l'autorisation de counsulter les likes",HttpStatus.FORBIDDEN); 
        }
    
    Optional<Post> opPost = postRepository.findById(postId);

    if (!opPost.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Post post = opPost.get();

    List<Jaime> likes = repository.findByPost(post);

    List<Utilisateur> usersWhoLikedPost = likes.stream()
            .map(Jaime::getUtilisateur)
            .collect(Collectors.toList());

    return new ResponseEntity<>(usersWhoLikedPost, HttpStatus.OK);

    }
}
