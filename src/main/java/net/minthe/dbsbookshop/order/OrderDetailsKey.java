package net.minthe.dbsbookshop.order;

import java.io.Serializable;

/**
 * Created by Michael Kelley on 10/14/2018
 */
public class OrderDetailsKey implements Serializable {
    private long ono;
    private String isbn;

    public OrderDetailsKey(long order, String isbn) {
        this.ono = order;
        this.isbn = isbn;
    }

    public OrderDetailsKey() {}

    public long getOrder() {
        return ono;
    }

    public void setOrder(long order) {
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
        int result = Long.hashCode(ono);
        result = 31 * result + isbn.hashCode();
        return result;
    }
}
