package net.minthe.dbsbookshop.book;

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
        addPaginationInformation(model, books);

        return "book/subject_view";
    }

    @GetMapping("/book/search")
    public String searchPage() {
        return "book/search";
    }

    @GetMapping("/book/author_search")
    public String searchBooksByAuthor(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                      @RequestParam(value = "author") String author,
                                      Model model) {
        Page<Book> books = bookRepository.findBooksByAuthorLikeIgnoreCase(
                "%" + author + "%",
                PageRequest.of(page, size));

        model.addAttribute("books", books);
        model.addAttribute("author", author);
        addPaginationInformation(model, books);

        return "book/author_view";
    }

    @GetMapping("/book/title_search")
    public String searchBooksByTitle(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "title") String title,
                                     Model model) {
        Page<Book> books = bookRepository.findBooksByTitleLikeIgnoreCase(
                "%" + title + "%",
                PageRequest.of(page, size));

        model.addAttribute("books", books);
        model.addAttribute("book_title", title);
        addPaginationInformation(model, books);

        return "book/title_view";
    }

    private void addPaginationInformation(Model model, Page<Book> books) {
        model.addAttribute("nextPage", books.getPageable().getPageNumber() + 1);
        model.addAttribute("hasNextPage", books.hasNext());
        model.addAttribute("prevPage", books.getPageable().getPageNumber() - 1);
        model.addAttribute("hasPrevPage", books.hasPrevious());
    }

}
