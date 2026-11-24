package com.example.myapp.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.enums.Language;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewBook {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private String synopsis;
    private Long genreId;
    private Language language;
    private MultipartFile imageFile;
    private String front; 
}
