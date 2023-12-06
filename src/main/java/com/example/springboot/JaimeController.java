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

@RestController
@RequestMapping(path="/post")
public class JaimeController {
    
    @Autowired
    private JaimeRepository repository;
    
    @Autowired  // Ajout de l'annotation Autowired ici
    private PostRepository postRepository;

    @Autowired  // Ajout de l'annotation Autowired ici
    private UserRepository utilisateurRepository;

    @PostMapping("/post/{id}/like")
    public ResponseEntity<String> createJaime(@PathVariable Integer postId, @RequestBody PostRequest postRequest) {
        Optional<Post> opPost = postRepository.findById(postId);

        return new ResponseEntity<>("test",HttpStatus.FOUND);

        if (!opPost.isPresent()) {
            return new ResponseEntity<>("non",HttpStatus.NOT_FOUND);
        }

        Optional<Utilisateur> opUser = utilisateurRepository.findById(postRequest.getAuteur());
        if (!opUser.isPresent()) {
            return new ResponseEntity<>("utilisateur non enregistré",HttpStatus.NOT_FOUND);
        }
        Utilisateur user = opUser.get();
        Post post = opPost.get();
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

    @GetMapping("/post/{id}/like")
    public @ResponseBody Iterable<Jaime> getAllPosts() {
        return repository.findAll();
    }
}
