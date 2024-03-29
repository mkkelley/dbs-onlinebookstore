package net.minthe.dbsbookshop.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    @Query(value="select distinct subject from books", nativeQuery = true)
    List<String> listDistinctSubject();

    Page<Book> findAll(Pageable pageable);

    Page<Book> findBooksBySubjectIgnoreCase(String subject, Pageable pageable);

    Page<Book> findBooksByAuthorLikeIgnoreCase(String author, Pageable pageable);

    Page<Book> findBooksByTitleLikeIgnoreCase(String title, Pageable pageable);

    @Query(value = "select * from books where trim(isbn) = :isbn", nativeQuery = true)
    Optional<Book> findByIsbn(@Param("isbn") String isbn);
}
