package net.minthe.dbsbookshop.member;

/**
 * Created by Michael Kelley on 10/22/2018
 */
public class UserAuthenticationRequest {
    private String userid;
    private String password;

    public UserAuthenticationRequest(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public UserAuthenticationRequest() {}

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

    @Override
    public String toString() {
        return "UserAuthenticationRequest{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuthenticationRequest that = (UserAuthenticationRequest) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
