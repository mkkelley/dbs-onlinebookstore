package net.minthe.dbsbookshop.order;

/**
 * Created by Michael Kelley on 10/26/2018
 * <p>
 * Class for user order creation, values are not trusted
 */
public class OrderForm {
    private boolean oneClick;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private int shipZip;
    private boolean newCc;
    private String newCcn;
    private String newCcType;

    public String getNewCcn() {
        return newCcn;
    }

    public void setNewCcn(String newCcn) {
        this.newCcn = newCcn;
    }

    public String getNewCcType() {
        return newCcType;
    }

    public void setNewCcType(String newCcType) {
        this.newCcType = newCcType;
    }

    public boolean isNewCc() {
        return newCc;
    }

    public void setNewCc(boolean newCc) {
        this.newCc = newCc;
    }

    public boolean isOneClick() {
        return oneClick;
    }

    public void setOneClick(boolean oneClick) {
        this.oneClick = oneClick;
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

        OrderForm that = (OrderForm) o;

        if (oneClick != that.oneClick) return false;
        if (shipZip != that.shipZip) return false;
        if (newCc != that.newCc) return false;
        if (shipAddress != null ? !shipAddress.equals(that.shipAddress) : that.shipAddress != null) return false;
        if (shipCity != null ? !shipCity.equals(that.shipCity) : that.shipCity != null) return false;
        if (shipState != null ? !shipState.equals(that.shipState) : that.shipState != null) return false;
        if (newCcn != null ? !newCcn.equals(that.newCcn) : that.newCcn != null) return false;
        return newCcType != null ? newCcType.equals(that.newCcType) : that.newCcType == null;
    }

    @Override
    public int hashCode() {
        int result = (oneClick ? 1 : 0);
        result = 31 * result + (shipAddress != null ? shipAddress.hashCode() : 0);
        result = 31 * result + (shipCity != null ? shipCity.hashCode() : 0);
        result = 31 * result + (shipState != null ? shipState.hashCode() : 0);
        result = 31 * result + shipZip;
        result = 31 * result + (newCc ? 1 : 0);
        result = 31 * result + (newCcn != null ? newCcn.hashCode() : 0);
        result = 31 * result + (newCcType != null ? newCcType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderSubmission{" +
                "oneClick=" + oneClick +
                ", shipAddress='" + shipAddress + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipZip=" + shipZip +
                ", newCc=" + newCc +
                ", newCcn='" + newCcn + '\'' +
                ", newCcType='" + newCcType + '\'' +
                '}';
    }
}
