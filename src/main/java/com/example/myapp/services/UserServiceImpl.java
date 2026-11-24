package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Users;
import com.example.myapp.enums.Role;
import com.example.myapp.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String cryptPassword(String password) {
        password = passwordEncoder.encode(password);
        return password;
    }

    @Override
    public Users create(Users user) {
        String password = this.cryptPassword(user.getPassword());
        user.setPassword(password);

        if(user.getRole() == null) user.setRole(Role.USER);
        
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Users getUser(String username) {
        return userRepository.findById(username).orElseThrow();
    }

    @Override
    public Users getConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName(); // Duda, como se que es el nombre?
            return this.getUser(username);
        }
        
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Users update(String username, Users newUser) {
        Users user = this.getUser(username);
        String password = this.cryptPassword(newUser.getPassword());

        user.setUsername(newUser.getUsername());
        user.setPassword(password);
        user.setValoration(newUser.getValoration());
        user.setRole(newUser.getRole());

        return user;
    }

    @Override
    public void delete(String username) {
        userRepository.deleteById(username);
    }

}
