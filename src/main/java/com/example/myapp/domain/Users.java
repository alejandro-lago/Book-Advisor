package com.example.myapp.domain;

import com.example.myapp.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Users {

    @Id
    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotNull
    private Role role;

    @NotEmpty
    private String password;
    
    @ManyToOne
    private Valoration valoration;
}
