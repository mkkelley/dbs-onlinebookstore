package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Member;
import net.minthe.dbsbookshop.model.Order;
import net.minthe.dbsbookshop.model.OrderSubmission;
import net.minthe.dbsbookshop.model.OrderSubmissionValidator;
import net.minthe.dbsbookshop.repo.LoginService;
import net.minthe.dbsbookshop.repo.MemberRepository;
import net.minthe.dbsbookshop.repo.OrderRepository;
import net.minthe.dbsbookshop.repo.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * Created by Michael Kelley on 10/26/2018
 */
@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @InitBinder("orderSubmission")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new OrderSubmissionValidator());
    }

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService, LoginService loginService, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.loginService = loginService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/order/{ono}")
    public String viewOrder(@PathVariable long ono, Model model) {
        Optional<Order> o = orderRepository.findById(ono);
        if (!o.isPresent()) {
            return "redirect:/order";
        }

        Order order = o.get();
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", order.getOrderDetailsList());
        model.addAttribute("total", orderRepository.getOrderTotal(order.getOno()));

        return "order/order_view";
    }

    @PostMapping("/order/new")
    public String submitOrder(@Valid @ModelAttribute OrderSubmission orderSubmission) {
        if (!orderService.canOrder(loginService.getUser())) {
            return "redirect:/cart";
        }
        Order order;
        if (orderSubmission.isOneClick()) {
            order = orderService.oneClickOrder(
                    loginService.getUser(),
                    Timestamp.from(Instant.now())
            );
        } else {
            if (orderSubmission.isNewCc()) {
                Member m = loginService.getUser();
                m.setCreditcardnumber(orderSubmission.getNewCcn());
                m.setCreditcardtype(orderSubmission.getNewCcType());
                memberRepository.save(m);
            }
            order = orderService.generateOrder(
                    loginService.getUser(),
                    Timestamp.from(Instant.now()),
                    orderSubmission.getShipAddress(),
                    orderSubmission.getShipCity(),
                    orderSubmission.getShipState(),
                    orderSubmission.getShipZip());
        }

        return "redirect:/order/" + order.getOno();
    }

    @GetMapping("/order/new")
    public String newOrder(Model model) {

        OrderSubmission order = new OrderSubmission();
        model.addAttribute("order", order);
        return "order/order_new";
    }

    @GetMapping("/order")
    public String listOrders(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
            Model model) {
        Page<Order> orders = orderRepository.findByUserid(loginService.getUser(), PageRequest.of(page, size));

        model.addAttribute("orders", orders);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("hasNextPage", orders.hasNext());
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("hasPrevPage", orders.hasPrevious());

        return "order/order_list";
    }
}
