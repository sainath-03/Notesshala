package com.example.notesshala.service;

import com.example.notesshala.Model.User;
import com.example.notesshala.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public User register(User u){
        u.setPassword(encoder.encode(u.getPassword()));
        return userRepo.save(u);
    }

    public User authenticate(String username,String rawPassword){
        return userRepo.findByUsername(username)
                .filter(u-> encoder.matches(rawPassword,u.getPassword()))
                .orElse(null);
    }
}
