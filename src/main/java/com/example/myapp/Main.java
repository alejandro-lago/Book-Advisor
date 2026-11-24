 package com.example.myapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.myapp.domain.Genre;
import com.example.myapp.domain.Users;
import com.example.myapp.enums.Role;
import com.example.myapp.services.GenreService;
import com.example.myapp.services.UserService;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
    CommandLineRunner initData(GenreService genreService, UserService userService) {
        return args -> {
            genreService.create(new Genre(null, "Action", "Action"));
			genreService.create(new Genre(null,"Comedy", "Comedy"));
			genreService.create(new Genre(null,"Drama", "Drama"));
			genreService.create(new Genre(null,"Adventure", "Adventure"));
			genreService.create(new Genre(null,"Fantasy", "Fantasy"));
			genreService.create(new Genre(null,"Horror", "Horror"));
			genreService.create(new Genre(null,"Thriller", "Thriller"));

			userService.create(new Users("pajas", Role.USER, "algo", null));
			userService.create(new Users("algo", Role.MANAGER, "algo", null));
			userService.create(new Users("jefe", Role.ADMIN, "algo", null));
        };
    }

}
