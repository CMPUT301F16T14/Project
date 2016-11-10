package ca.ualberta.project.cmput301f16t14.beep;

import io.searchbox.annotations.JestId;

/**
 * Created by Aries on 06/11/2016.
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
    private Profile profile;

    public void Account(String username, Profile profile){
        this.username = username;
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getUsername(){
        return username;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
