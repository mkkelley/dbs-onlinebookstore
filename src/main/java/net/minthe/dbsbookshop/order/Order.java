package net.minthe.dbsbookshop.order;

import net.minthe.dbsbookshop.member.Member;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Kelley on 10/14/2018
 *
 * Domain object
 */
@Entity
@Table(name = "orders")
public class Order {
    @ManyToOne
    @JoinColumn(name = "userid")
    private Member userid;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQUENCE")
    @SequenceGenerator(name = "ORDER_SEQUENCE", sequenceName = "ORDER_SEQUENCE", allocationSize = 1)
    private long ono;
    @Column(nullable = false)
    private Timestamp received;
    private Timestamp shipped;
    @Column(name = "shipaddress")
    private String shipAddress;
    @Column(name = "shipcity")
    private String shipCity;
    @Column(name = "shipstate")
    private String shipState;
    @Column(name = "shipzip")
    private int shipZip;

    @OneToMany(mappedBy = "ono")
    private List<OrderDetails> orderDetailsList = new ArrayList<>();

    public Order(Member userid, long ono, Timestamp received, Timestamp shipped, String shipAddress, String shipCity, String shipState, int shipZip) {
        this.userid = userid;
        this.ono = ono;
        this.received = received;
        this.shipped = shipped;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipState = shipState;
        this.shipZip = shipZip;
    }

    public Order() {}

    @Override
    public String toString() {
        return "Order{" +
                "userid=" + userid +
                ", ono=" + ono +
                ", received=" + received +
                ", shipped=" + shipped +
                ", shipAddress='" + shipAddress + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipZip='" + shipZip + '\'' +
                '}';
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public Member getUserid() {
        return userid;
    }

    public void setUserid(Member userid) {
        this.userid = userid;
    }

    public long getOno() {
        return ono;
    }

    public void setOno(long ono) {
        this.ono = ono;
    }

    public Timestamp getReceived() {
        return received;
    }

    public void setReceived(Timestamp received) {
        this.received = received;
    }

    public Timestamp getShipped() {
        return shipped;
    }

    public void setShipped(Timestamp shipped) {
        this.shipped = shipped;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public int getShipZip() {
        return shipZip;
    }

    public void setShipZip(int shipZip) {
        this.shipZip = shipZip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (ono != order.ono) return false;
        if (!userid.equals(order.userid)) return false;
        if (!received.equals(order.received)) return false;
        if (shipped != null ? !shipped.equals(order.shipped) : order.shipped != null) return false;
        if (shipAddress != null ? !shipAddress.equals(order.shipAddress) : order.shipAddress != null) return false;
        if (shipCity != null ? !shipCity.equals(order.shipCity) : order.shipCity != null) return false;
        if (shipState != null ? !shipState.equals(order.shipState) : order.shipState != null) return false;
        return shipZip == order.shipZip;
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + Long.hashCode(ono);
        result = 31 * result + received.hashCode();
        result = 31 * result + (shipped != null ? shipped.hashCode() : 0);
        result = 31 * result + (shipAddress != null ? shipAddress.hashCode() : 0);
        result = 31 * result + (shipCity != null ? shipCity.hashCode() : 0);
        result = 31 * result + (shipState != null ? shipState.hashCode() : 0);
        result = 31 * result + Integer.hashCode(shipZip);
        return result;
    }
}
