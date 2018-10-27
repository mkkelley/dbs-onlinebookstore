package net.minthe.dbsbookshop.cart;

import java.io.Serializable;

public class CartKey implements Serializable {
    private String userid;
    private String isbn;

    public CartKey(String userid, String isbn) {
        this.userid = userid;
        this.isbn = isbn;
    }

    public CartKey() { }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

        CartKey cartKey = (CartKey) o;

        if (!userid.equals(cartKey.userid)) return false;
        return isbn.equals(cartKey.isbn);
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + isbn.hashCode();
        return result;
    }
}