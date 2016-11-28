package ca.ualberta.cs.linkai.beep;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by lincolnqi on 2016-11-11.
 *
 * @author LinKai
 * @see SignUpActivity
 * @see WelcomeActivity
 *
 * <p>Here is the class to save all information about account
 *    Singleton Pattern is used
 * </p>
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
    private int RequestNum;
    private String vehicleInfo;
    private Double totolRating;
    private Double totolCompletion;
    /**
     * Define the user type
     * "1" to be a driver
     * "2" to be a rider
     * "3" to be both a rider or driver
     */
    private int UserType;

    /**
     * This method is a constructor which initialize the account
     * @param username
     * @param phone
     * @param email
     * @param AccountType
     */
    public Account(String username, String phone, String email, int AccountType){
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.UserType = AccountType;
    }
    public Double getAvgRating() {
        return totolRating/totolCompletion;
    }

    public void setRating(Double rateNum) {
        totolRating = totolRating + rateNum;
        totolCompletion = totolCompletion + 1;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
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

    public int getRequestNum() {
        return RequestNum;
    }

    public void setRequestNum(int num) {
        this.RequestNum = num;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }
    //getter and setter about JestId
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}
