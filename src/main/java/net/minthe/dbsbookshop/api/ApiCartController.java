package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.cart.CartRepository;
import net.minthe.dbsbookshop.member.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Michael Kelley on 11/1/2018
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ApiCartController {
    private final CartRepository cartRepository;
    private final LoginService loginService;

    @Autowired
    public ApiCartController(CartRepository cartRepository, LoginService loginService) {
        this.cartRepository = cartRepository;
        this.loginService = loginService;
    }

    @GetMapping("/cart/count")
    public int getCartCount() {
        return cartRepository.getCartCount(loginService.getUser());
    }
}
