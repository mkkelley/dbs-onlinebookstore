package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Michael Kelley on 10/26/2018
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
