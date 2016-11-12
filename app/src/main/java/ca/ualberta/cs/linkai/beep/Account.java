package ca.ualberta.cs.linkai.beep;

import io.searchbox.annotations.JestId;

/**
 * Created by lincolnqi on 2016-11-11.
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
    private Integer phone;
    private String email;

    public void Account(String username, Integer phone, String email){
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public void Account(String username, Integer phone){
        this.username = username;
        this.phone = phone;
    }

    public void Account(String username, String email){
        this.username = username;
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

}
