package ca.ualberta.project.cmput301f16t14.beep;

/**
 * Created by Aries on 06/11/2016.
 */

public class Account {

    private String username;
    private Profile profile;
    private Integer role; // if rider, role = 0; if driver, role = 1.

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
