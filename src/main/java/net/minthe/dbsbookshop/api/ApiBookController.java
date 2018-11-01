package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.book.Book;
import net.minthe.dbsbookshop.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Michael Kelley on 10/31/2018
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ApiBookController {
    private final BookRepository bookRepository;

    @Autowired
    public ApiBookController(BookRepository bookRepository) {this.bookRepository = bookRepository;}

    @GetMapping("/book")
    public Page<Book> bookList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/book/subject")
    public List<String> subjectList() {
        return bookRepository.listDistinctSubject();
    }

    @GetMapping("/book/subject/{subject}")
    public Page<Book> booksBySubject(
            @PathVariable String subject,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return bookRepository.findBooksBySubjectIgnoreCase(subject, PageRequest.of(page, size));
    }
}
