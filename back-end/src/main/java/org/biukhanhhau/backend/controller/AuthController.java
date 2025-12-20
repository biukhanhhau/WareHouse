package org.biukhanhhau.backend.controller;

import org.apache.coyote.Response;
import org.biukhanhhau.backend.model.User;
import org.biukhanhhau.backend.repository.UserRepository;
import org.biukhanhhau.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String authenticationAndGetToken(@RequestBody User authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }
        else{
            throw new UsernameNotFoundException("Invalid login request");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser){
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username is existed");
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if(newUser.getRole() == null || newUser.getRole().isEmpty()) {
            newUser.setRole("USER");
        }
        User savedUser = userRepository.save(newUser);

        return ResponseEntity.ok("Registration successful user: " + savedUser.getUsername());
    }
}
