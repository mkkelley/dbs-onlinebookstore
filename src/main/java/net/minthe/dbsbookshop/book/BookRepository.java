package net.minthe.dbsbookshop.book;

import net.minthe.dbsbookshop.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    @Query(value="select distinct subject from books", nativeQuery = true)
    List<String> listDistinctSubject();

    Page<Book> findBooksBySubjectIgnoreCase(String subject, Pageable pageable);

    Page<Book> findBooksByAuthorLikeIgnoreCase(String author, Pageable pageable);

    Page<Book> findBooksByTitleLikeIgnoreCase(String title, Pageable pageable);
}
