package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.AddUserRequestBody;
import com.example.PostRequest;

@Controller // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UtilisateurController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewUser (@RequestBody AddUserRequestBody requestBody) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    String userName = requestBody.getUsername();
    String password = requestBody.getPassword();
    String email = requestBody.getEmail();
    String role = requestBody.getRole();

    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setUsername(userName);
    utilisateur.setEmail(email);
    utilisateur.setRole(role);
    utilisateur.setPassword(password);
    userRepository.save(utilisateur);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Utilisateur> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }
}