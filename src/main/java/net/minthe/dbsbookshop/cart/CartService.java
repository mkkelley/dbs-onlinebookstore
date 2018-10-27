package net.minthe.dbsbookshop.cart;

import net.minthe.dbsbookshop.book.Book;
import net.minthe.dbsbookshop.member.Member;
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

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ShoppingCart getCart(Member user) {
        List<Cart> carts = cartRepository.findByUserid(user);
        return new ShoppingCart(carts, user);
    }

    public void addBook(Member user, Book book) {
        ShoppingCart cart = getCart(user);
        cart.addBook(book);
        persist(cart);
    }

    public void setQty(Member user, Book book, int qty) {
        ShoppingCart cart = getCart(user);
        Optional<Cart> c = cart.setQty(book, qty);
        if (!c.isPresent()) {
            persist(cart);
        } else {
            cartRepository.delete(c.get());
        }
    }

    public BigDecimal getTotal(Member user) {
        return cartRepository.getTotalForMember(user);
    }

    private void persist(ShoppingCart shoppingCart) {
        cartRepository.saveAll(shoppingCart.getCarts());
    }
}
