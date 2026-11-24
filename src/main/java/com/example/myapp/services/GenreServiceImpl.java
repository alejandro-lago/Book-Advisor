package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Genre;
import com.example.myapp.repositories.BookRepository;
import com.example.myapp.repositories.GenreRepository;

import jakarta.transaction.Transactional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getGenre(Long id) {
        return genreRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public Genre update(Long id, Genre newGenre) {
        Genre genre = this.getGenre(id);

        genre.setName(newGenre.getName());
        genre.setDescription(newGenre.getDescription());

        return genre;
    }

    @Transactional
    public void delete(Long id) {
        
        genreRepository.deleteById(id);
    }
}
