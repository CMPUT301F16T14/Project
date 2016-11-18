package ca.ualberta.cs.linkai.beep;

import io.searchbox.annotations.JestId;

/**
 * Created by lincolnqi on 2016-11-11.
 *
 * @author LinKai
 * @see SignUpActivity
 * @see WelcomeActivity
 *
 * <p>Here is the class to save all information about account</p>
 * <ul>
 *     <li>AccountId</li>
 *     <li>UserName</li>
 *     <li>PhoneNumber</li>
 *     <li>EmailAddress</li>
 * </ul>
 */

public class Account {

    @JestId
    private String id;
    private String username;
    private String phone;
    private String email;

    /**
     * This method is a constructor which initialize the account
     * @param username
     * @param phone
     * @param email
     */
    public Account(String username, String phone, String email){
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    //getter and setter about JestId
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}
