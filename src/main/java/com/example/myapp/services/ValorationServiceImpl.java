package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Users;
import com.example.myapp.domain.Valoration;
import com.example.myapp.repositories.ValorationRepository;

import jakarta.transaction.Transactional;

@Service
public class ValorationServiceImpl implements ValorationService{

    @Autowired
    private ValorationRepository valorationRepository;

    @Override
    public Valoration create(Valoration valoration) {
        return valorationRepository.save(valoration);
    }

    @Override
    public Valoration getValoration(Long id) {
        return valorationRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Valoration> getValorationByBook(Book book) {
        return valorationRepository.findByBook(book);
    }

    @Override
    public List<Valoration> getValorationByBookAndUser(Book book, Users user) {
        return valorationRepository.findByBookAndUser(book, user);
    }

    @Override
    public List<Valoration> getUserCommentOnBook(String username, Long bookId) {
        return valorationRepository.findByUserUsernameAndBookId(username, bookId);
    }

@Override
    public float getAveragePoints(Book book) {
        List<Valoration> valorations = this.getValorationByBook(book);

        if(valorations.isEmpty()) return 0f;

        float total = 0f;
        for (Valoration valoration : valorations) {
            total += valoration.getPoints();
        }
        return Math.round((total / valorations.size()) * 100f) / 100f;
    }

    @Override
    public List<Valoration> getAllValorations() {
        return valorationRepository.findAll();
    }

    @Override
    @Transactional
    public Valoration update(Long id, Valoration newValoration) {
        Valoration valoration = this.getValoration(id);

        valoration.setUser(newValoration.getUser());
        valoration.setBook(newValoration.getBook());
        valoration.setPoints(newValoration.getPoints());
        valoration.setComments(newValoration.getComments());

        return valoration;
    }

    @Override
    public void delete(Long id) {
        valorationRepository.deleteById(id);
    }

@Override
    public long getVotes(Book book) {
        return this.getValorationByBook(book).size();
    }

    @Override
    @Transactional
    public void deleteByBookAndUser(Book book, Users user) {
        valorationRepository.deleteByBookAndUser(book, user);
    }
}
