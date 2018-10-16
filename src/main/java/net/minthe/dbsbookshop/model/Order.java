package net.minthe.dbsbookshop.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Michael Kelley on 10/14/2018
 */
@Entity
@Table(name="orders")
public class Order {
    @ManyToOne
    private Member userid;
    @Id
    private int ono;
    @Column(nullable=false)
    private Timestamp received;
    private Timestamp shipped;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private String shipZip;

    public Order(Member userid, int ono, Timestamp received, Timestamp shipped, String shipAddress, String shipCity, String shipState, String shipZip) {
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

    public Member getUserid() {
        return userid;
    }

    public void setUserid(Member userid) {
        this.userid = userid;
    }

    public int getOno() {
        return ono;
    }

    public void setOno(int ono) {
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

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
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
        return shipZip != null ? shipZip.equals(order.shipZip) : order.shipZip == null;
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + ono;
        result = 31 * result + received.hashCode();
        result = 31 * result + (shipped != null ? shipped.hashCode() : 0);
        result = 31 * result + (shipAddress != null ? shipAddress.hashCode() : 0);
        result = 31 * result + (shipCity != null ? shipCity.hashCode() : 0);
        result = 31 * result + (shipState != null ? shipState.hashCode() : 0);
        result = 31 * result + (shipZip != null ? shipZip.hashCode() : 0);
        return result;
    }
}
