package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.member.LoginService;
import net.minthe.dbsbookshop.member.Member;
import net.minthe.dbsbookshop.member.MemberRepository;
import net.minthe.dbsbookshop.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Created by Michael Kelley on 11/3/2018
 */
@RestController
@RequestMapping("/api")
public class ApiOrderController {
    private final OrderRepository orderRepository;
    private final LoginService loginService;
    private final OrderService orderService;
    private final MemberRepository memberRepository;

    @InitBinder("orderForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new OrderFormValidator());
    }

    @Autowired
    public ApiOrderController(OrderRepository orderRepository, LoginService loginService, OrderService orderService, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.loginService = loginService;
        this.orderService = orderService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/order/list")
    public List<Order> listOrders() {
        return orderRepository.findByUseridWithDetails(loginService.getUser());
    }

    @GetMapping("/order/{ono}")
    public Order getOrder(@PathVariable("ono") long ono) {
        Optional<Order> optionalOrder = orderRepository.findById(ono);
        return optionalOrder.orElseThrow();
    }

    @PostMapping("/order/new")
    public ResponseEntity<?> submitOrder(@Valid @RequestBody OrderForm orderForm,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getModel());
        }

        Member member = loginService.getUser();
        if (orderForm.isNewCc()) {
            member.setCreditcardnumber(orderForm.getNewCcn());
            member.setCreditcardtype(orderForm.getNewCcType());
            memberRepository.save(member);
        }

        if (!orderService.canOrder(member)) {
            return ResponseEntity.badRequest().body("Member is unable to order.");
        }

        Order order = orderService.generateOrder(
                member,
                Timestamp.from(Instant.now()),
                orderForm.getShipAddress(),
                orderForm.getShipCity(),
                orderForm.getShipState(),
                orderForm.getShipZip());
        return ResponseEntity.ok(order);
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
