package com.example.myapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Genre;

public interface BookRepository extends JpaRepository<Book, Long>{
    List<Book> findByTitle(String title);
    List<Book> findByGenre(Genre genre);
    List<Book> findByTitleAndGenre(String title, Genre genre);
    boolean existsByGenreId(Long id);
}
