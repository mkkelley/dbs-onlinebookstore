package net.minthe.dbsbookshop.api;

import net.minthe.dbsbookshop.book.Book;
import net.minthe.dbsbookshop.book.BookRepository;
import net.minthe.dbsbookshop.cart.Cart;
import net.minthe.dbsbookshop.cart.CartRepository;
import net.minthe.dbsbookshop.cart.CartService;
import net.minthe.dbsbookshop.member.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Michael Kelley on 11/1/2018
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ApiCartController {
    private final CartRepository cartRepository;
    private final LoginService loginService;
    private final CartService cartService;
    private final BookRepository bookRepository;

    @Autowired
    public ApiCartController(CartRepository cartRepository, LoginService loginService, CartService cartService, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.loginService = loginService;
        this.cartService = cartService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/cart/count")
    public int getCartCount() {
        return cartRepository.getCartCount(loginService.getUser());
    }

    @GetMapping("/cart/list")
    public List<Cart> viewCart() {
        return cartRepository.findByUserid(loginService.getUser());
    }

    @DeleteMapping("/cart/{isbn}")
    public void removeFromCart(@PathVariable String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow();
        cartService.setQty(loginService.getUser(), book, 0);
    }

    @PutMapping("/cart/{isbn}")
    public ResponseEntity<?> updateCart(@PathVariable String isbn, @RequestBody Map<String, String> data) {
        if (data == null || data.isEmpty() || !data.containsKey("qty")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        int qty;

        try {
            qty = Integer.parseInt(data.get("qty"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Book book = bookRepository.findByIsbn(isbn).orElseThrow();
        cartService.setQty(loginService.getUser(), book, qty);
        return ResponseEntity.ok(null);
    }
}
