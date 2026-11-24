package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Genre;

public interface GenreService {
    Genre create(Genre genre);
    Genre getGenre(Long id);
    List<Genre> getAllGenres();
    Genre update(Long id, Genre newGenre);
    void delete(Long id);
}
