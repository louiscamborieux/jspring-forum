package com.example.springboot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.PostRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller // This means that this class is a Controller
@RequestMapping(path="/post") // This means URL's start with /demo (after Application path)
public class PostController {
  @Autowired
  private PostRepository repository;
  @Autowired
  private UserRepository utilisateurRepository;
  @Autowired
  private PostService postService;

  @Autowired
  private AuthenticationUtils authenticationUtils;


  @PostMapping(path="/add") // Map ONLY POST Requests
  public ResponseEntity<?> createPost (@RequestBody PostRequest postRequest) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    Utilisateur utilisateurAuthentifie = authenticationUtils.getUtilisateurAuthentifie();
    if (utilisateurAuthentifie == null) {
      return new ResponseEntity<>("Vous n'êtes pas authentifié",HttpStatus.UNAUTHORIZED);
    }

    String titre = postRequest.getTitre();
    String contenu = postRequest.getContenu();
    int auteurID = postRequest.getAuteur();

    Optional<Utilisateur> opUser = utilisateurRepository.findById(auteurID);
    
    if (!opUser.isPresent() || auteurID != utilisateurAuthentifie.getId()) {
      return new ResponseEntity<>("Vous n'êtes pas authentifié en tant que l'auteur",HttpStatus.FORBIDDEN);
    }
    Utilisateur auteur = opUser.get();

    Post post = new Post();
    post.setTitre(titre);
    post.setContenu(contenu);
    post.setAuteur(auteur);
    repository.save(post);
    return  ResponseEntity.ok(post);
  }

  @PutMapping("/{id}/edit")
  public ResponseEntity<?> modifierPost(@PathVariable Integer id, @RequestBody PostRequest postRequest ) {
      
      String titre = postRequest.getTitre();
      String contenu = postRequest.getContenu();
      Utilisateur utilisateurAuthentifie = authenticationUtils.getUtilisateurAuthentifie();
      
      if (utilisateurAuthentifie == null) {
        return new ResponseEntity<>("Vous n'êtes pas authentifié",HttpStatus.UNAUTHORIZED);
      }

      Optional<Post> opPost = repository.findById(id);
      if (!opPost.isPresent()) {
            return new ResponseEntity<>("Post introuvable",HttpStatus.NOT_FOUND);
      }
      Post post = opPost.get();

      if (!post.getAuteur().equals(utilisateurAuthentifie)) {
      return new ResponseEntity<>("Vous n'êtes pas authentifié en tant que l'auteur",HttpStatus.FORBIDDEN);
      }

      

      post.setTitre(titre);
      post.setContenu(contenu);
      repository.save(post);

      return  ResponseEntity.ok(post);
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<?> getAllPosts() {
    Utilisateur utilisateurAuthentifie = authenticationUtils.getUtilisateurAuthentifie();
    if (utilisateurAuthentifie !=null ) {
        return repository.findAll();
      }

    return postService.getAllPostsRestreints();
  }

  @GetMapping(path="/{id}")
  public ResponseEntity<?>  getPost(@PathVariable Integer id) {
      Optional<Post> opPost = repository.findById(id);

      if (!opPost.isPresent()) {
            return new ResponseEntity<>("Post introuvable",HttpStatus.NOT_FOUND);
      }
      Utilisateur utilisateurAuthentifie = authenticationUtils.getUtilisateurAuthentifie();

      Post post = opPost.get();
      if (utilisateurAuthentifie !=null ) {
        return ResponseEntity.ok(post);
      }
      else {
            PostRestreint postRestreint = post.toRestreint();
            return ResponseEntity.ok(postRestreint);
      }


      
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> supprimerPost(@PathVariable Integer id, @RequestBody PostRequest postRequest) {
      int auteurID = postRequest.getAuteur();
      
      Optional<Post> opPost = repository.findById(id);

      if (!opPost.isPresent()) {
            return new ResponseEntity<>("Post introuvable",HttpStatus.NOT_FOUND);
      }

      Post post = opPost.get();

      Optional<Utilisateur> opUser = utilisateurRepository.findById(auteurID);
      if (!opUser.isPresent()) {
            return new ResponseEntity<>("Utilisateur introuvable",HttpStatus.NOT_FOUND);
      }

      Utilisateur user = opUser.get();

      if (user != post.getAuteur() && !user.isModerator()) {
        return new ResponseEntity<>("Vous n'êtes autorisé à supprimmer ce post.",HttpStatus.FORBIDDEN);
      }

      repository.delete(post);

      return new ResponseEntity<>("Post supprimé.", HttpStatus.NO_CONTENT);
  }
}