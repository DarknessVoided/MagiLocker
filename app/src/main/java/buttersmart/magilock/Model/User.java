package buttersmart.magilock.Model;

/**
 * Created by Xaiver on 18/12/2017.
 */

public class User {
    private String email,password, name, phonenumber;

    public User() {
    }

    public User(String email, String password, String name, String phonenumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
