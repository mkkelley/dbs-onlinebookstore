package net.minthe.dbsbookshop.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import net.minthe.dbsbookshop.book.Book;
import net.minthe.dbsbookshop.cart.Cart;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Michael Kelley on 10/14/2018
 *
 * Domain object
 */
@Entity(name = "OrderDetails")
@Table(name = "odetails")
@IdClass(OrderDetailsKey.class)
public class OrderDetails {

    @Id
    @ManyToOne
    @JoinColumn(name = "ono")
    @JsonBackReference
    private Order ono;

    @Id
    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book isbn;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private BigDecimal price;

    public OrderDetails(Order ono, Book isbn, int qty, BigDecimal price) {
        this.ono = ono;
        this.isbn = isbn;
        this.qty = qty;
        this.price = price;
    }

    public OrderDetails(Order order, Cart cart) {
        this.ono = order;
        this.isbn = cart.getIsbn();
        this.price = cart.getIsbn().getPrice();
        this.qty = cart.getQty();
    }

    public OrderDetails() {}

    @Override
    public String toString() {
        return "OrderDetails{" +
                "ono=" + ono +
                ", isbn='" + isbn + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }

    public Order getOno() {
        return ono;
    }

    public void setOno(Order ono) {
        this.ono = ono;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetails that = (OrderDetails) o;

        if (qty != that.qty) return false;
        if (!ono.equals(that.ono)) return false;
        if (!isbn.equals(that.isbn)) return false;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        int result = ono.hashCode();
        result = 31 * result + isbn.hashCode();
        result = 31 * result + qty;
        result = 31 * result + price.hashCode();
        return result;
    }
}
