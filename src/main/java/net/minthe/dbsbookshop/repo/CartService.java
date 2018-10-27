package net.minthe.dbsbookshop.repo;

import net.minthe.dbsbookshop.model.Book;
import net.minthe.dbsbookshop.model.Cart;
import net.minthe.dbsbookshop.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        List<Cart> carts = cartRepository.findByUserid(loginService.getUser());
        return new ShoppingCart(carts, loginService.getUser());
    }

    public void addBook(Book book) {
        ShoppingCart cart = getCart();
        cart.addBook(book);
        persist(cart);
    }

    public void setQty(Book book, int qty) {
        ShoppingCart cart = getCart();
        Optional<Cart> c = cart.setQty(book, qty);
        if (!c.isPresent()) {
            persist(cart);
        } else {
            cartRepository.delete(c.get());
        }
    }

    public BigDecimal getTotal() {
        return cartRepository.getTotalForMember(loginService.getUser());
    }

    private void persist(ShoppingCart shoppingCart) {
        cartRepository.saveAll(shoppingCart.getCarts());
    }
}
