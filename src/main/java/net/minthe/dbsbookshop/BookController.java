package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Book;
import net.minthe.dbsbookshop.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/book/subject")
    public String listSubjects(Model model) {
        List<String> subjectList = bookRepository.listDistinctSubject();
        model.addAttribute("subjects", subjectList);
        return "book/subject_list";
    }

    @GetMapping("/book/subject/{subject}")
    public String listBooksBySubject(@PathVariable String subject,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     Model model) {
        Page<Book> books = bookRepository.findBooksBySubjectIgnoreCase(subject, PageRequest.of(page, size));


        model.addAttribute("books", books);
        model.addAttribute("subject", subject);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("hasNextPage", books.hasNext());
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("hasPrevPage", books.hasPrevious());

        return "book/subject_view";
    }
}
