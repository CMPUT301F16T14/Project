package ca.ualberta.project.cmput301f16t14.beep;

import io.searchbox.annotations.JestId;

/**
 * Created by linkai on 11/10/16.
 */
public class Account {
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    @JestId
    private  String id;
    private String username;
    private String phone;
    private String email;

    public Account(String username, String phone, String email){
        this.username = username;
        this.phone = phone;
        this.email = email;
    }


    public String getUsername(){
        return username;
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
