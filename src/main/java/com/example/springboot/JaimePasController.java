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
public class JaimePasController {
    
    @Autowired
    private JaimePasRepository repository;

    @Autowired
    private JaimeRepository jaimeRepository;
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository utilisateurRepository;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @PostMapping("/{postId}/dislike")
    public ResponseEntity<String> createJaimePas(@PathVariable Integer postId) {
        Utilisateur utilisateurAuthentifie = authenticationUtils.getUtilisateurAuthentifie();
        if (utilisateurAuthentifie == null) {
          return new ResponseEntity<>("Vous n'êtes pas authentifié",HttpStatus.UNAUTHORIZED);
        }

        Optional<Post> opPost = postRepository.findById(postId);
        if (!opPost.isPresent()) {
            return new ResponseEntity<>("Post non trouvé",HttpStatus.NOT_FOUND);
        }
        Post post = opPost.get();

        if (utilisateurAuthentifie.equals(post.getAuteur())) {
            return new ResponseEntity<>("Vous ne pouvez pas exprimer votre désapprobation à vos propres posts",HttpStatus.FORBIDDEN);
        }

        Optional<Jaime> opJaime = jaimeRepository.findByUtilisateurAndPost(utilisateurAuthentifie, post);
        if (opJaime.isPresent()) {
            jaimeRepository.delete(opJaime.get());
        }

        Optional<JaimePas> opJaimePas = repository.findByUtilisateurAndPost(utilisateurAuthentifie, post);
        if (!opJaimePas.isPresent()) {
            JaimePas reaction = new JaimePas();
            reaction.setPost(post);
            reaction.setUtilisateur(utilisateurAuthentifie);
            repository.save(reaction);
            return new ResponseEntity<>("Vous n'aimez pas ce post",HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("vous avez déjà disliké ce post",HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{postId}/dislikes")
    public ResponseEntity <?> getWhoLikedPost(@PathVariable Integer postId) {
        Utilisateur utilisateurAuthentifie = authenticationUtils.getUtilisateurAuthentifie();
        if (utilisateurAuthentifie == null) {
          return new ResponseEntity<>("Vous n'êtes pas authentifié",HttpStatus.UNAUTHORIZED);
        }
        if (!utilisateurAuthentifie.isModerator()) {
                       return new ResponseEntity<>("Vous n'avez pas l'autorisation de consulter les dislikes",HttpStatus.FORBIDDEN); 
        }
        Optional<Post> opPost = postRepository.findById(postId);

    if (!opPost.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Post post = opPost.get();

    List<JaimePas> likes = repository.findByPost(post);

    List<Utilisateur> listeUtilisateurs = likes.stream()
            .map(JaimePas::getUtilisateur)
            .collect(Collectors.toList());

    return new ResponseEntity<>(listeUtilisateurs, HttpStatus.OK);

    }
}
