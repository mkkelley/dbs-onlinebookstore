package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.member.LoginService;
import net.minthe.dbsbookshop.order.Order;
import net.minthe.dbsbookshop.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Michael Kelley on 11/3/2018
 */
@RestController
@RequestMapping("/api")
public class ApiOrderController {
    private final OrderRepository orderRepository;
    private final LoginService loginService;

    @Autowired
    public ApiOrderController(OrderRepository orderRepository, LoginService loginService) {
        this.orderRepository = orderRepository;
        this.loginService = loginService;
    }

    @GetMapping("/order/list")
    public List<Order> listOrders() {
        return orderRepository.findByUserid(loginService.getUser());
    }
}
