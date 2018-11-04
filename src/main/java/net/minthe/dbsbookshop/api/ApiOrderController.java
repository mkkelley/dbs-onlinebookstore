package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.member.LoginService;
import net.minthe.dbsbookshop.order.Order;
import net.minthe.dbsbookshop.order.OrderRepository;
import net.minthe.dbsbookshop.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Created by Michael Kelley on 11/3/2018
 */
@RestController
@RequestMapping("/api")
public class ApiOrderController {
    private final OrderRepository orderRepository;
    private final LoginService loginService;
    private final OrderService orderService;

    @Autowired
    public ApiOrderController(OrderRepository orderRepository, LoginService loginService, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.loginService = loginService;
        this.orderService = orderService;
    }

    @GetMapping("/order/list")
    public List<Order> listOrders() {
        return orderRepository.findByUseridWithDetails(loginService.getUser());
    }

    @GetMapping("/order/oneclick")
    public ResponseEntity<?> oneClickOrder() {
        if (!orderService.canOrder(loginService.getUser())) {
            return ResponseEntity.badRequest().body(null);
        }

        Order order = orderService.oneClickOrder(
                loginService.getUser(), Timestamp.from(Instant.now()));
        return ResponseEntity.ok(order);
    }
}
