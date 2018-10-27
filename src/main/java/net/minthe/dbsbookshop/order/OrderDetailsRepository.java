package net.minthe.dbsbookshop.order;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Michael Kelley on 10/26/2018
 */
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, OrderDetailsKey> {
}
