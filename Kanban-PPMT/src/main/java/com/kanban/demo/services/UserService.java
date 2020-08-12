package com.kanban.demo.services;

import com.kanban.demo.domain.User;
import com.kanban.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        //UserName should be unique
        //Password and confirm Password should match
        //We don't persist or show the confirmPassword

        return userRepository.save(newUser);
    }
}
