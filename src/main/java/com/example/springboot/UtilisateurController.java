package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UtilisateurController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewUser (@RequestParam String name
      , @RequestParam String email,  @RequestParam String role) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setUsername(name);
    utilisateur.setEmail(email);
    utilisateur.setRole(role);
    userRepository.save(utilisateur);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Utilisateur> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }
}