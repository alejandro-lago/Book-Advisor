package com.example.myapp.dtos;

import java.time.LocalDate;

import com.example.myapp.enums.Language;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDetails {

    private String front;
    private Long id;
    private String title;
    private Integer year;
    private String author;
    private String synopsis;
    private Language language;
    private LocalDate registration_date = LocalDate.now();
    private String genre;
    private Long votes;
    private Float points;
    private Integer pointsRound;
}
