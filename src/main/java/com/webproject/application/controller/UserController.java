package com.webproject.application.controller;

import com.webproject.application.dto.AuthRequest;
import com.webproject.application.dto.RegisterRequest;
import com.webproject.application.entity.User;
import com.webproject.application.repository.UserRepository;
import com.webproject.application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest)  {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("A felhasználónév vagy a jelszó hibás");
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateToken(authRequest.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest user) {

        if(userRepository.findByUserName(user.getUsername()) != null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Már létezik ilyen felhasználo");

        User newRegistration = new User();
        newRegistration.setEmail(user.getEmail());
        newRegistration.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newRegistration.setUserName(user.getUsername());

        userRepository.save(newRegistration);
        return ResponseEntity.ok("Sikeres regisztráció!");
    }
}
