package net.minthe.dbsbookshop.cart;

import net.minthe.dbsbookshop.member.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Michael Kelley on 10/22/2018
 */
@Repository
public interface CartRepository extends CrudRepository<Cart, CartKey> {
    List<Cart> findByUserid(Member userid);

    @Query(value = "select sum(price * qty) " +
            "from CART " +
            "       inner join BOOKS on CART.ISBN = BOOKS.ISBN " +
            "where userid = :userid", nativeQuery = true)
    BigDecimal getTotalForMember(@Param("userid") Member userid);

    @Query(value = "select count(*) " +
            "from cart " +
            "where userid = :userid", nativeQuery = true)
    int getCartCount(@Param("userid") Member userid);
}
