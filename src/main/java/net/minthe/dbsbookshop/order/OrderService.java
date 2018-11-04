package net.minthe.dbsbookshop.order;

import net.minthe.dbsbookshop.cart.Cart;
import net.minthe.dbsbookshop.cart.CartRepository;
import net.minthe.dbsbookshop.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Kelley on 10/26/2018
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public Order oneClickOrder(Member member, Timestamp received) {
        return generateOrder(
                member,
                received,
                member.getAddress(),
                member.getCity(),
                member.getState(),
                member.getZip()
        );
    }

    public boolean canOrder(Member member) {
        return cartRepository.findByUserid(member).size() > 0 &&
                !"".equals(member.getCreditcardnumber()) &&
                !"".equals(member.getCreditcardtype());

    }

    public Order generateOrder(
            Member member,
            Timestamp received,
            String shipAddress,
            String shipCity,
            String shipState,
            int zip) {
        Order order = new Order();
        order.setUserid(member);
        order.setReceived(received);
        order.setShipAddress(shipAddress);
        order.setShipCity(shipCity);
        order.setShipState(shipState);
        order.setShipZip(zip);


        List<Cart> cartList = cartRepository.findByUserid(member);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (Cart c : cartList) {
            OrderDetails orderDetails = new OrderDetails(order, c);
            orderDetailsList.add(orderDetails);
            cartRepository.delete(c);
        }

        order.setOrderDetailsList(orderDetailsList);

        order = orderRepository.save(order);
        orderDetailsRepository.saveAll(orderDetailsList);
        return order;
    }
}
