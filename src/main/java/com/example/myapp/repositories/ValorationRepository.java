package com.example.myapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Users;
import com.example.myapp.domain.Valoration;


public interface ValorationRepository extends JpaRepository<Valoration, Long>{
    List<Valoration> findByBook(Book book);
    List<Valoration> findByBookAndUser(Book book, Users user);
    List<Valoration> findByUserUsernameAndBookId(String username, Long bookId);
    void deleteByBookAndUser(Book book, Users user);
}
