package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.OrderDetails;
import net.minthe.dbsbookshop.model.OrderDetailsKey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Michael Kelley on 10/26/2018
 */
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, OrderDetailsKey> {
}
