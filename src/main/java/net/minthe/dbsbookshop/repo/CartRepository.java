package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Cart;
import net.minthe.dbsbookshop.model.CartKey;
import net.minthe.dbsbookshop.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael Kelley on 10/22/2018
 */
@Repository
public interface CartRepository extends CrudRepository<Cart, CartKey> {
    List<Cart> findByUserid(Member userid);
}
