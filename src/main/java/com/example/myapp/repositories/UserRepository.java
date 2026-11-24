package com.example.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Users;

public interface UserRepository extends JpaRepository<Users, String>{

}
