package net.minthe.dbsbookshop.order;

import net.minthe.dbsbookshop.member.LoginService;
import net.minthe.dbsbookshop.member.Member;
import net.minthe.dbsbookshop.member.MemberRepository;
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
        binder.addValidators(new OrderFormValidator());
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
    public String submitOrder(@Valid @ModelAttribute OrderForm orderForm) {
        if (!orderService.canOrder(loginService.getUser())) {
            return "redirect:/cart";
        }
        Order order;
        if (orderForm.isOneClick()) {
            order = orderService.oneClickOrder(
                    loginService.getUser(),
                    Timestamp.from(Instant.now())
            );
        } else {
            if (orderForm.isNewCc()) {
                Member m = loginService.getUser();
                m.setCreditcardnumber(orderForm.getNewCcn());
                m.setCreditcardtype(orderForm.getNewCcType());
                memberRepository.save(m);
            }
            order = orderService.generateOrder(
                    loginService.getUser(),
                    Timestamp.from(Instant.now()),
                    orderForm.getShipAddress(),
                    orderForm.getShipCity(),
                    orderForm.getShipState(),
                    orderForm.getShipZip());
        }

        return "redirect:/order/" + order.getOno();
    }

    @GetMapping("/order/new")
    public String newOrder(Model model) {

        OrderForm order = new OrderForm();
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
