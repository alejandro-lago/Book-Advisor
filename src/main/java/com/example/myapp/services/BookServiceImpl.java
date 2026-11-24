package com.example.myapp.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Genre;
import com.example.myapp.repositories.BookRepository;

@Primary
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public List<Book> getBooksByTitleAndGenre(String title, Genre genre) {
        return bookRepository.findByTitleAndGenre(title, genre);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book update(Long id, Book newBook) {
        Book book = bookRepository.findById(id).orElseThrow();

book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setYear(newBook.getYear());
        book.setGenre(newBook.getGenre());
        book.setSynopsis(newBook.getSynopsis());
        book.setRegistration_date(LocalDate.now());
        book.setLanguage(newBook.getLanguage());
        book.setFront(newBook.getFront());

        return book;
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
