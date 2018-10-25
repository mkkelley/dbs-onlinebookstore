package net.minthe.dbsbookshop.model;

import java.io.Serializable;

/**
 * Created by Michael Kelley on 10/14/2018
 */
public class OrderDetailsKey implements Serializable {
    private int ono;
    private String isbn;

    public OrderDetailsKey(int order, String isbn) {
        this.ono = order;
        this.isbn = isbn;
    }

    public OrderDetailsKey() {}

    public int getOrder() {
        return ono;
    }

    public void setOrder(int order) {
        this.ono = order;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailsKey that = (OrderDetailsKey) o;

        if (ono != that.ono) return false;
        return isbn.equals(that.isbn);
    }

    @Override
    public int hashCode() {
        int result = ono;
        result = 31 * result + isbn.hashCode();
        return result;
    }
}
