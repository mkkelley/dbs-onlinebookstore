package net.minthe.dbsbookshop.member;

/**
 * Created by Michael Kelley on 10/28/2018
 *
 * This is the class used to represent unsanitized and unvalidated
 * user input.
 */
class MemberForm {
    private String fname;
    private String lname;
    private String address;
    private String city;
    private String state;
    private int zip;

    private String phone;
    private String email;

    private String userid;
    private String password;
    private String creditcardtype;
    private String creditcardnumber;

    String getFname() {
        return fname;
    }

    void setFname(String fname) {
        this.fname = fname;
    }

    String getLname() {
        return lname;
    }

    void setLname(String lname) {
        this.lname = lname;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    int getZip() {
        return zip;
    }

    void setZip(int zip) {
        this.zip = zip;
    }

    String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getUserid() {
        return userid;
    }

    void setUserid(String userid) {
        this.userid = userid;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getCreditcardtype() {
        return creditcardtype;
    }

    void setCreditcardtype(String creditcardtype) {
        this.creditcardtype = creditcardtype;
    }

    String getCreditcardnumber() {
        return creditcardnumber;
    }

    void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", creditcardtype='" + creditcardtype + '\'' +
                ", creditcardnumber='" + creditcardnumber + '\'' +
                '}';
    }
}
