package com.example.myapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Genre;
import com.example.myapp.domain.Users;
import com.example.myapp.dtos.Contact;
import com.example.myapp.dtos.FilterBook;
import com.example.myapp.dtos.HeadersBook;
import com.example.myapp.dtos.NewUsers;
import com.example.myapp.enums.Consult;
import com.example.myapp.services.BookService;
import com.example.myapp.services.GenreService;
import com.example.myapp.services.UserService;
import com.example.myapp.services.ValorationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ValorationService valorationService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenreService genreService;

    @GetMapping("/accessError")
    public String showAccessErrorView() {

        return "error/accessError";
    }

    @GetMapping("/signup")
    public String showSignUn(Model model) {
        model.addAttribute("formUser", new NewUsers());
        return "signupView";
    }

    @PostMapping("/signup/submit")
public String postSignUp(@Valid @ModelAttribute("formUser") NewUsers newUser,
                         BindingResult bindingResult,
                         HttpServletRequest request) { // inyectamos request

    if (!bindingResult.hasErrors()) {
        // Crear usuario
        Users user = new Users();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword()); // sin encriptar aquí, se hace en userService
        userService.create(user);

        Users createdUser = userService.getUser(user.getUsername());

        // Crear autoridad
        List<SimpleGrantedAuthority> authorities = 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + createdUser.getRole().name()));

        // Crear authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
            createdUser.getUsername(),
            null,
            authorities
        );

        // Establecer en SecurityContext
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Guardar en sesión explícitamente
        request.getSession(true).setAttribute(
            "SPRING_SECURITY_CONTEXT",
            SecurityContextHolder.getContext()
        );
    }

    return "redirect:/public/home";
}

    @GetMapping({ "/", "/home" })
    public String showHomeView() {
        return "homeView";
    }

    @GetMapping("/who")
    public String showWhoView() {
        return "whoView";
    }

    @GetMapping("/contact")
    public String showContactView(Model model) {
        model.addAttribute("consultList", Consult.values());
        model.addAttribute("contactForm", new Contact());

        return "contactView";
    }

    @PostMapping("/contact/submit")
    public String postContactForm(@Valid @ModelAttribute("contactForm") Contact contact,
            BindingResult bindingResult) {

        return "redirect:/";
    }

    @GetMapping("/book")
    public String showBookView(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<HeadersBook> headersBooks = new ArrayList<>();

        for (Book book : books) {
            HeadersBook hb = new HeadersBook();

            hb.setId(book.getId());
            hb.setFront(book.getFront());
            hb.setTitle(book.getTitle());
            hb.setAuthor(book.getAuthor());
            hb.setSynopsis(book.getSynopsis());
            hb.setLanguage(book.getLanguage());
            hb.setGenreId(book.getGenre().getId());
            hb.setGenre(book.getGenre().getName());
            hb.setPoints(valorationService.getAveragePoints(book));

            headersBooks.add(hb);
        }

        model.addAttribute("filterBook", new FilterBook());
        model.addAttribute("genreList", genreService.getAllGenres());
        model.addAttribute("bookList", headersBooks);

        return "bookListView";
    }

    @GetMapping("/book/filter")
    public String searchBooks(@ModelAttribute("filterBook") HeadersBook filterBook, Model model) {
        String title = filterBook.getTitle();
        Genre genre = genreService.getGenre(filterBook.getGenreId());

        List<Book> books;

        if ((title == null || title.isEmpty()) && genre == null) {
            books = bookService.getAllBooks();
        } else if ((title == null || title.isEmpty())) {
            books = bookService.getBooksByGenre(genre);
        } else if (genre == null) {
            books = bookService.getBooksByTitle(title);
        } else {
            books = bookService.getBooksByTitleAndGenre(title, genre);
        }

        model.addAttribute("bookList", books);
        model.addAttribute("genreList", genreService.getAllGenres());
        model.addAttribute("filterBook", filterBook);

        return "bookListView";
    }

    @GetMapping("/genre")
    public String showGenreListView(Model model) {
        model.addAttribute("genreList", genreService.getAllGenres());
        return "genreListView";
    }

}
