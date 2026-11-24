package com.example.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

}
