package net.minthe.dbsbookshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael Kelley on 10/24/2018
 */
public class ShoppingCart {
    private Member member;
    private List<Cart> cartList;

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
    }

    public List<Cart> getCarts() {
        return cartList;
    }

    public HashMap<Book, Integer> getMap() {
        HashMap<Book, Integer> out = new HashMap<>();
        for (Cart c : cartList) {
            out.put(c.getIsbn(), c.getQty());
        }
        return out;
    }

    public void setQty(Book book, int qty) {
        for (Cart c : cartList) {
            if (c.getIsbn().equals(book)) {
                c.setQty(qty);
                return;
            }
        }

        // not found in list, so new item, set qty to one

        addBook(book);
    }

    public void addBook(Book book) {
        for (Cart c : cartList) {
            if (c.getIsbn().equals(book)) {
                c.setQty(c.getQty() + 1);
                return;
            }
        }

        // not found in existing cart, so add a new one

        Cart c = new Cart();
        c.setIsbn(book);
        c.setUserid(member);
        c.setQty(1);

        cartList.add(c);
    }
}
