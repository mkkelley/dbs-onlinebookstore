package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Book;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Michael Kelley on 10/15/2018
 */
public interface BookRepository extends CrudRepository<Book, String> {
}
