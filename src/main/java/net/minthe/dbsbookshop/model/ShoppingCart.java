package net.minthe.dbsbookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Michael Kelley on 10/24/2018
 */
public class ShoppingCart {
    private Member member;
    private List<Cart> cartList;
    private boolean cartChanged;
    private HashMap<Book, Integer> bookQtyMap;

    public ShoppingCart(Iterable<Cart> cart) {
        cartList = new ArrayList<>();
        for (Cart c : cart) {
            if (this.member == null) {
                this.member = c.getUserid();
            } else if (!this.member.equals(c.getUserid())) {
                throw new IllegalArgumentException("Passed cart contains more than one member's items.");
            }

            cartList.add(c);
        }
        cartChanged = true;
    }

    public List<Cart> getCarts() {
        return cartList;
    }

    public HashMap<Book, Integer> getMap() {
        if (cartChanged) {
            HashMap<Book, Integer> out = new HashMap<>();
            for (Cart c : cartList) {
                out.put(c.getIsbn(), c.getQty());
            }
            this.bookQtyMap = out;
            return out;
        } else {
            return bookQtyMap;
        }
    }

    /**
     * Update the quantity of a book in the cart
     * @param book Book to add to cart
     * @param qty Quantity
     * @return Optional.empty() if quantity was positive, Optional.of(cart) if quantity was
     *         zero or negative
     */
    public Optional<Cart> setQty(Book book, int qty) {
        Optional<Cart> c = cartList.stream()
                .filter( cart -> cart.getIsbn().equals(book) )
                .findFirst();

        if (!c.isPresent()) {
            addBook(book, qty);
        } else if (qty <= 0) {
            cartList.remove(c.get());
            return c;
        } else { // qty > 0 && c.isPresent()
            c.get().setQty(qty);
        }
        return Optional.empty();
    }

    public void addBook(Book book) {
        addBook(book, 1);
    }

    public void addBook(Book book, int qty) {
        for (Cart c : cartList) {
            if (c.getIsbn().equals(book)) {
                c.setQty(c.getQty() + qty);
                return;
            }
        }

        // not found in existing cart, so add a new one

        Cart c = new Cart();
        c.setIsbn(book);
        c.setUserid(member);
        c.setQty(qty);

        cartList.add(c);
        cartChanged = true;
    }
}
