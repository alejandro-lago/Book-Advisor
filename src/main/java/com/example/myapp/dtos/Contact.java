package com.example.myapp.dtos;

import com.example.myapp.enums.Consult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {

    private String name;
    private String mail;
    private Consult consult;
    private String comment;
    private boolean conditions;
}
