package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Book;
import net.minthe.dbsbookshop.repo.BookRepository;
import net.minthe.dbsbookshop.repo.CartService;
import net.minthe.dbsbookshop.repo.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Michael Kelley on 10/22/2018
 */
@Controller
public class CartController {
    private final BookRepository bookRepository;
    private final CartService cartService;
    private final LoginService loginService;

    @Autowired
    public CartController(BookRepository bookRepository, CartService cartService, LoginService loginService) {
        this.bookRepository = bookRepository;
        this.cartService = cartService;
        this.loginService = loginService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        HashMap<Book, Integer> cartMap = cartService.getCart(loginService.getUser()).getMap();

        model.addAttribute("cartMap", cartMap);
        model.addAttribute("total", cartService.getTotal(loginService.getUser()));

        return "cart/cart_view";
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestParam String isbn) {
        Optional<Book> book = bookRepository.findById(isbn);
        if (!book.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        cartService.addBook(loginService.getUser(), book.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Map<String, String> formData) {
        for (var entry : formData.entrySet()) {
            String isbn = entry.getKey();
            String qtyString = entry.getValue();

            Optional<Book> b = bookRepository.findById(isbn);
            if (!b.isPresent()) continue;
            int qty = Integer.parseInt(qtyString);

            cartService.setQty(loginService.getUser(), b.get(), qty);
        }

        return "redirect:/cart";
    }
}
