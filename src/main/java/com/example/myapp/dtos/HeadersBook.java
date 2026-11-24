package com.example.myapp.dtos;

import com.example.myapp.enums.Language;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadersBook {
    private Long id;
    private String front;
    private String title;
    private String author;
    private String synopsis;
    private Language language;
    private Float points;
    private Long genreId;
    private String genre;
}
