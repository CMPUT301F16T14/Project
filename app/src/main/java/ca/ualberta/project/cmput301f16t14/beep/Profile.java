package ca.ualberta.project.cmput301f16t14.beep;

/**
 * Created by linkai on 11/10/16.
 */
public class Profile {
    private Integer phone;
    private String email;

    public Profile(Integer phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
