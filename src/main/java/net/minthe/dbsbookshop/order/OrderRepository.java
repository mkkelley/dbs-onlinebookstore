package net.minthe.dbsbookshop.order;

import net.minthe.dbsbookshop.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

/**
 * Created by Michael Kelley on 10/26/2018
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
    Page<Order> findByUserid(Member userid, Pageable pageable);

    @Query(value = "SELECT SUM(PRICE * QTY) " +
            "FROM odetails " +
            "WHERE ono = :ono", nativeQuery = true)
    BigDecimal getOrderTotal(@Param("ono") long ono);
}
