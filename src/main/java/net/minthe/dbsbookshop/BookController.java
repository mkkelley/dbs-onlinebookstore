package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Book;
import net.minthe.dbsbookshop.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Michael Kelley on 10/22/2018
 */
@Controller
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {this.bookRepository = bookRepository;}

    @GetMapping("/book")
    public String listBooks(Model model) {
        Iterable<Book> books = bookRepository.findAll();

        model.addAttribute("books", books);
        return "book/book_list";
    }
}
