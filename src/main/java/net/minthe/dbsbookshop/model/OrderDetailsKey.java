package net.minthe.dbsbookshop.model;

import java.io.Serializable;

/**
 * Created by Michael Kelley on 10/14/2018
 */
public class OrderDetailsKey implements Serializable {
    private Order ono;
    private Book isbn;

    public OrderDetailsKey(Order order, Book isbn) {
        this.ono = order;
        this.isbn = isbn;
    }

    public OrderDetailsKey() {}

    public Order getOrder() {
        return ono;
    }

    public void setOrder(Order order) {
        this.ono = order;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailsKey that = (OrderDetailsKey) o;

        if (!ono.equals(that.ono)) return false;
        return isbn.equals(that.isbn);
    }

    @Override
    public int hashCode() {
        int result = ono.hashCode();
        result = 31 * result + isbn.hashCode();
        return result;
    }
}
