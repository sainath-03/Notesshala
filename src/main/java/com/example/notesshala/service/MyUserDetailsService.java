package com.example.notesshala.service;

import com.example.notesshala.Model.User;
import com.example.notesshala.Model.UserPrincipal;
import com.example.notesshala.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(username).orElse(null);
        if(user==null){
            throw new UsernameNotFoundException("User 404");

        }

        return new UserPrincipal(user);

    }
}
