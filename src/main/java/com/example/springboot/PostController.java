package com.example.springboot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.PostRequest;

@Controller // This means that this class is a Controller
@RequestMapping(path="/post") // This means URL's start with /demo (after Application path)
public class PostController {
  @Autowired
  private PostRepository repository;
  @Autowired
  private UserRepository utilisateurRepository;

  @PostMapping(path="/add") // Map ONLY POST Requests
  public ResponseEntity<String> createPost (@RequestBody PostRequest postRequest) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    String titre = postRequest.getTitre();
    String contenu = postRequest.getContenu();
    int auteurID = postRequest.getAuteur();

    Optional<Utilisateur> opUser = utilisateurRepository.findById(auteurID);
    if (!opUser.isPresent()) {
            return new ResponseEntity<>("auteur non présent",HttpStatus.NOT_FOUND);
    }
    Utilisateur auteur = opUser.get();
    Post post = new Post();
    post.setTitre(titre);
    post.setContenu(contenu);
    post.setAuteur(auteur);
    repository.save(post);
    return  new ResponseEntity<>("Saved",HttpStatus.OK);
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Post> getAllPosts() {
    // This returns a JSON or XML with the users
    return repository.findAll();
  }
}