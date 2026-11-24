package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Genre;

public interface BookService {
    Book create(Book book);
    Book getBook(Long id);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByGenre(Genre genre);
    List<Book> getBooksByTitleAndGenre(String title, Genre genre);
    List<Book> getAllBooks();
    Book update(Long id, Book newBook);
    void delete(Long id);
}
