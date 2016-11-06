package ca.ualberta.project.cmput301f16t14.beep;

/**
 * Created by administrator on 2016-10-13.
 */
public class Profile {

    private String email;
    private String phone; // sting or integer???

    public Profile(String username, String phone, String email) {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
