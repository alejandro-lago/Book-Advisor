package com.example.myapp.dtos;

import com.example.myapp.domain.Genre;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterBook {

    private String title;
    private Genre genre;
}
