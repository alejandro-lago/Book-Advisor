package com.example.myapp.domain;

import java.time.LocalDate;
import java.util.List;

import com.example.myapp.enums.Language;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String front;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @Column(name = "publication_year")
    private Integer year;

    private String synopsis;

    private LocalDate registration_date = LocalDate.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull
    @ManyToOne
    private Genre genre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Valoration> valorations;
}
