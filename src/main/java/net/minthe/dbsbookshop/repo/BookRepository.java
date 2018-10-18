package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Michael Kelley on 10/15/2018
 */
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
