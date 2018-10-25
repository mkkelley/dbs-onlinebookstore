package net.minthe.dbsbookshop.model;

import javax.persistence.*;

/**
 * Created by Michael Kelley on 10/14/2018
 */
@Entity
@Table(name="cart")
@IdClass(CartKey.class)
public class Cart {
    @Id
    @ManyToOne
    @JoinColumn(name="userid")
    private Member userid;

    @Id
    @ManyToOne
    @JoinColumn(name="isbn")
    private Book isbn;

    @Column(nullable=false)
    private int qty;

    public Member getUserid() {
        return userid;
    }

    public void setUserid(Member userid) {
        this.userid = userid;
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
}
