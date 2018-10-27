package net.minthe.dbsbookshop.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Michael Kelley on 10/14/2018
 */
@Entity
@Table(name="members")
public class Member {
    @Column(nullable = false)
    private String fname;
    @Column(nullable = false)
    private String lname;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private int zip;

    private String phone;
    private String email;

    @Id
    private String userid;
    private String password;
    private String creditcardtype;
    private String creditcardnumber;

    public Member(String fname, String lname, String address, String city, String state, int zip, String phone, String email, String userid, String password, String creditcardtype, String creditcardnumber) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.creditcardtype = creditcardtype;
        this.creditcardnumber = creditcardnumber;
    }

    public Member() {}

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreditcardtype() {
        return creditcardtype;
    }

    public void setCreditcardtype(String creditcardtype) {
        this.creditcardtype = creditcardtype;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    @Override
    public String toString() {
        return "Member{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", creditcardtype='" + creditcardtype + '\'' +
                ", creditcardnumber='" + creditcardnumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (!fname.equals(member.fname)) return false;
        if (!lname.equals(member.lname)) return false;
        if (!address.equals(member.address)) return false;
        if (!city.equals(member.city)) return false;
        if (!state.equals(member.state)) return false;
        if (zip != member.zip) return false;
        if (phone != null ? !phone.equals(member.phone) : member.phone != null) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        if (userid != null ? !userid.equals(member.userid) : member.userid != null) return false;
        if (password != null ? !password.equals(member.password) : member.password != null) return false;
        if (creditcardtype != null ? !creditcardtype.equals(member.creditcardtype) : member.creditcardtype != null)
            return false;
        return creditcardnumber != null ? creditcardnumber.equals(member.creditcardnumber) : member.creditcardnumber == null;
    }

    @Override
    public int hashCode() {
        int result = fname.hashCode();
        result = 31 * result + lname.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + Integer.hashCode(zip);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (creditcardtype != null ? creditcardtype.hashCode() : 0);
        result = 31 * result + (creditcardnumber != null ? creditcardnumber.hashCode() : 0);
        return result;
    }
}
