package net.minthe.dbsbookshop;

import net.minthe.dbsbookshop.model.Book;
import net.minthe.dbsbookshop.repo.BookRepository;
import net.minthe.dbsbookshop.repo.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    public CartController(BookRepository bookRepository, CartService cartService) {
        this.bookRepository = bookRepository;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        HashMap<Book, Integer> cartMap = cartService.getCart().getMap();

        model.addAttribute("cartMap", cartMap);

        return "cart/cart_view";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String isbn, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Optional<Book> book = bookRepository.findById(isbn);
        if (!book.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Unable to add requested book to cart.");
            return "redirect:/book";
        }

        return "redirect:" + request.getHeader("referrer");
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Map<String, String> formData) {
        for (var entry : formData.entrySet()) {
            String isbn = entry.getKey();
            String qtyString = entry.getValue();

            Optional<Book> b = bookRepository.findById(isbn);
            if (!b.isPresent()) continue;
            int qty = Integer.parseInt(qtyString);

            cartService.setQty(b.get(), qty);
        }

        return "redirect:/cart";
    }
}
