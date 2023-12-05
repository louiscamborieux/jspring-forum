package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/post") // This means URL's start with /demo (after Application path)
public class PostController {
  @Autowired
  private PostRepository repository;

  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String createPost (@RequestParam String titre
      , @RequestParam String contenu,  @RequestParam Utilisateur auteur) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    Post post = new Post();
    post.setTitre(titre);
    post.setContenu(contenu);
    post.setAuteur(auteur);
    repository.save(post);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Post> getAllPosts() {
    // This returns a JSON or XML with the users
    return repository.findAll();
  }
}