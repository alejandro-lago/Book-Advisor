package com.example.myapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.myapp.domain.Users;
import com.example.myapp.services.UserService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users UserData = userService.getUser(username);
        
        if(UserData == null) throw (new UsernameNotFoundException("User not found"));
        return User
            .withUsername(username)
            .roles(UserData.getRole().name())
            .password(UserData.getPassword())
            .build();
    }

}
