package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Book;
import net.minthe.dbsbookshop.model.Cart;
import net.minthe.dbsbookshop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Michael Kelley on 10/22/2018
 */
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final LoginService loginService;

    @Autowired
    public CartService(CartRepository cartRepository, BookRepository bookRepository, LoginService loginService) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.loginService = loginService;
    }

    public ShoppingCart getCart() {
        if (!loginService.userLoggedIn()) {
            return null;
        }

        List<Cart> carts = cartRepository.findByUserid(loginService.getUser());
        return new ShoppingCart(carts);
    }

    public void addBook(Book book) {
        if (!loginService.userLoggedIn()) {
            return;
        }

        ShoppingCart cart = getCart();
        cart.addBook(book);
        persist(cart);
    }

    public void setQty(Book book, int qty) {
        if (!loginService.userLoggedIn()) {
            return;
        }

        ShoppingCart cart = getCart();
        cart.setQty(book, qty);
        persist(cart);
    }

    private void persist(ShoppingCart shoppingCart) {
        cartRepository.saveAll(shoppingCart.getCarts());
    }
}
