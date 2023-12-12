package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicesCustom implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Utilisateur "+username+" non trouvé."));

        return user;

    }
}
