package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Users;
import com.example.myapp.domain.Valoration;

public interface ValorationService {
    Valoration create(Valoration valoration);
    Valoration getValoration(Long id);
    List<Valoration> getValorationByBook(Book book);
    List<Valoration> getUserCommentOnBook(String username, Long bookId);
    float getAveragePoints(Book book);
List<Valoration> getValorationByBookAndUser(Book book, Users user);
    List<Valoration> getAllValorations();
    Valoration update(Long id, Valoration newValoration);
    void delete(Long id);
    void deleteByBookAndUser(Book book, Users user);
    long getVotes(Book book);
}
