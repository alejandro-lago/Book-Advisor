package com.example.myapp.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.domain.Book;
import com.example.myapp.domain.Genre;
import com.example.myapp.domain.Users;
import com.example.myapp.domain.Valoration;
import com.example.myapp.dtos.BookDetails;
import com.example.myapp.dtos.NewBook;
import com.example.myapp.dtos.NewGenre;
import com.example.myapp.dtos.NewValoration;
import com.example.myapp.dtos.ValorationHeaders;
import com.example.myapp.enums.Language;
import com.example.myapp.services.BookService;
import com.example.myapp.services.GenreService;
import com.example.myapp.services.UserService;
import com.example.myapp.services.ValorationService;

import jakarta.validation.Valid;

@Controller
public class ModelController {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ValorationService valorationService;

    @Autowired
    private UserService userService;

    @GetMapping("/book/new")
    public String showNewBookView(Model model) {
        model.addAttribute("genreList", genreService.getAllGenres());
        model.addAttribute("languageList", Language.values());
        model.addAttribute("formNewBook", new NewBook());
        return "newBookView";
    }

    @PostMapping("/book/new/submit")
    public String postNewBookView(@Valid @ModelAttribute("formNewBook") NewBook newBook) {
        Book book = new Book();

        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setYear(newBook.getYear());
        book.setSynopsis(newBook.getSynopsis());
        book.setGenre(genreService.getGenre(newBook.getGenreId()));
        book.setLanguage(newBook.getLanguage());

        MultipartFile imageFile = newBook.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String folder = "uploads";
                Path folderLocation = Paths.get(folder).toAbsolutePath();
                if (!Files.exists(folderLocation)) {
                    Files.createDirectories(folderLocation);
                }

                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                Path filePath = folderLocation.resolve(filename);
                imageFile.transferTo(filePath.toFile());

                book.setFront("/uploads/" + filename);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bookService.create(book);
        return "redirect:/public/book";
    }

    @GetMapping("/book/{id}")
    public String showBookView(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        BookDetails details = new BookDetails();

        Float points = valorationService.getAveragePoints(book);
        Long votes = valorationService.getVotes(book);

        details.setFront(book.getFront());
        details.setId(book.getId());
        details.setTitle(book.getTitle());
        details.setYear(book.getYear());
        details.setAuthor(book.getAuthor());
        details.setSynopsis(book.getSynopsis());
        details.setLanguage(book.getLanguage());
        details.setRegistration_date(book.getRegistration_date());
        details.setGenre(book.getGenre().getName());
        details.setVotes(votes);
        details.setPoints(points);
        details.setPointsRound(Math.round(points));

        model.addAttribute("bookDetails", details);
        return "bookView";
    }

    @GetMapping("/book/delete/{id}")
    public String getMethodName(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/book/edit/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);

        NewBook newBook = new NewBook();
        newBook.setId(book.getId());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setYear(book.getYear());
        newBook.setSynopsis(book.getSynopsis());
        newBook.setLanguage(book.getLanguage());
        newBook.setGenreId(book.getGenre().getId());
        newBook.setFront(book.getFront());

        model.addAttribute("genreList", genreService.getAllGenres());
        model.addAttribute("languageList", Language.values());
        model.addAttribute("formEditBook", newBook);

        return "editBookView";
    }

    @PostMapping("/book/edit/submit")
    public String postMethodName(@ModelAttribute("formEditBook") NewBook newBook) {
        Book book = new Book();

        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setYear(newBook.getYear());
        book.setSynopsis(newBook.getSynopsis());
        book.setGenre(genreService.getGenre(newBook.getGenreId()));
        book.setLanguage(newBook.getLanguage());

        MultipartFile imageFile = newBook.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String folder = "uploads";
                Path folderLocation = Paths.get(folder).toAbsolutePath();
                if (!Files.exists(folderLocation)) {
                    Files.createDirectories(folderLocation);
                }

                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                Path filePath = folderLocation.resolve(filename);
                imageFile.transferTo(filePath.toFile());

                book.setFront("/uploads/" + filename);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bookService.update(newBook.getId(), book);

        return "redirect:/public/book";
    }

    @GetMapping("/book/valoration/{id}")
    public String showValorationListView(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        List<Valoration> valorations = valorationService.getValorationByBook(book);
        List<ValorationHeaders> headersValorations = new ArrayList<>();

        for (Valoration valoration : valorations) {
            ValorationHeaders vh = new ValorationHeaders();

            vh.setId(valoration.getId());
            vh.setUsername(valoration.getUser().getUsername());
            vh.setPoints(valoration.getPoints());
            vh.setComments(valoration.getComments());
            vh.setFront(valoration.getBook().getFront());

            headersValorations.add(vh);
        }

        model.addAttribute("book", book);
        model.addAttribute("valorationList", headersValorations);

        return "valorationListView";
    }

    @GetMapping("/book/valoration/new/{id}")
    public String showNewValorationView(@PathVariable Long id, Model model) {
        try {
            Book book = bookService.getBook(id);
            if (book == null) {
                return "redirect:/error/404";
            }

            // DTO para el formulario
            NewValoration formValoration = new NewValoration();
            formValoration.setBookId(id);

            model.addAttribute("formValoration", formValoration);
            model.addAttribute("book", book); // agregamos el libro para la vista

            return "newValorationView";
        } catch (Exception e) {
            e.printStackTrace(); // opcional: para debug
            return "redirect:/error/500";
        }
    }

    @PostMapping("/book/valoration/new/submit")
    public String postNewValoration(@ModelAttribute("formValoration") NewValoration newValoration,
            Authentication authentication) {
        Users currentUser = userService.getUser(authentication.getName());
        Book book = bookService.getBook(newValoration.getBookId());

        List<Valoration> existingValorations = valorationService.getValorationByBookAndUser(
                bookService.getBook(newValoration.getBookId()), currentUser);
        Long id;

        if (existingValorations.isEmpty()) {
            Valoration valoration = new Valoration();
            valoration.setPoints(newValoration.getPoints());
            valoration.setComments(newValoration.getComments());
            valoration.setUser(currentUser);
            valoration.setBook(book);

            id = book.getId();

            valorationService.create(valoration);
        } else {
            Valoration existingValoration = existingValorations.get(0);
            existingValoration.setPoints(newValoration.getPoints());
            existingValoration.setComments(newValoration.getComments());

            valorationService.update(existingValoration.getId(), existingValoration);
            id = existingValoration.getBook().getId();
        }

        return "redirect:/book/valoration/" + id;
    }

    @GetMapping("/valoration/delete/{id}")
    public String deleteValoration(@PathVariable Long id, Authentication authentication) {
        Valoration valoration = valorationService.getValoration(id);
        Long bookId = valoration.getBook().getId();

        // Solo permitir borrar si es ADMIN o si es USER/MANAGER y es su propia
        // valoración
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = valoration.getUser().getUsername().equals(authentication.getName());

        if (isAdmin || isOwner) {
            valorationService.delete(id);
        }

        return "redirect:/book/valoration/" + bookId;
    }

    @GetMapping("/genre")
    public String showGenreListView(Model model) {
        model.addAttribute("genreList", genreService.getAllGenres());
        return "genreListView";
    }

    @GetMapping("/genre/delete/{id}")
    public String deleteGenre(@PathVariable Long id, Model model) {
        genreService.delete(id);

        return "redirect:/genre";
    }

    @GetMapping("/genre/new")
    public String showNewGenreView(Model model) {
        model.addAttribute("formGenre", new NewGenre());

        return "newGenreView";
    }

    @PostMapping("/genre/new/submit")
    public String postMethodName(@Valid @ModelAttribute("formGenre") NewGenre newGenre) {
        Genre genre = new Genre();

        genre.setName(newGenre.getName());
        genre.setDescription(newGenre.getDescription());

        genreService.create(genre);

        return "redirect:/genre";
    }

    @GetMapping("/genre/edit/{id}")
    public String getEditGenreView(@PathVariable Long id, Model model) {
        Genre genre = genreService.getGenre(id);
        NewGenre newGenre = new NewGenre();

        newGenre.setId(id);
        newGenre.setName(genre.getName());
        newGenre.setDescription(genre.getDescription());

        model.addAttribute("formGenre", newGenre);

        return "editGenreView";
    }

    @PostMapping("/genre/edit/submit")
    public String postEditGenre(@Valid @ModelAttribute("formGenre") NewGenre newGenre) {
        Genre genre = new Genre();
        genre.setName(newGenre.getName());
        genre.setDescription(newGenre.getDescription());

        genreService.update(newGenre.getId(), genre);

        return "redirect:/genre";
    }

}
