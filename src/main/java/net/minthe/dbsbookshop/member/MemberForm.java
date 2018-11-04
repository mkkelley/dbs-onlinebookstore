package net.minthe.dbsbookshop.member;

/**
 * Created by Michael Kelley on 10/28/2018
 *
 * This is the class used to represent unsanitized and unvalidated
 * user input.
 */
public class MemberForm {
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

    public MemberForm() {}

    // Conversion is fine because we go more trusted -> less
    public MemberForm(Member member) {
        setFname(member.getFname());
        setLname(member.getLname());
        setAddress(member.getAddress());
        setCity(member.getCity());
        setState(member.getState());
        setZip(member.getZip());

        setPhone(member.getPhone());
        setEmail(member.getEmail());

        setUserid(member.getUserid());
        setPassword(member.getPassword());
        setCreditcardnumber(member.getCreditcardnumber());
        setCreditcardtype(member.getCreditcardtype());
    }

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
