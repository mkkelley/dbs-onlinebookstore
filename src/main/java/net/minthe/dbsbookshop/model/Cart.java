package net.minthe.dbsbookshop.model;

import javax.persistence.*;

/**
 * Created by Michael Kelley on 10/14/2018
 */
@Entity
@Table(name="carts")
@IdClass(CartKey.class)
public class Cart {
    @Id
    @ManyToOne
    private Member userid;

    @Id
    @ManyToOne
    private Book isbn;

    @Column(nullable=false)
    private int qty;

}
