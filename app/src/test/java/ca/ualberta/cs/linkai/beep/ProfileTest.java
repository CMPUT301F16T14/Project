package ca.ualberta.cs.linkai.beep;

import android.util.Log;
import android.widget.Toast;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by ting8 on 2016/11/14.
 */

public class ProfileTest {

    // rider1
    Account rider1 = new Account("testRiderName1", "7807101111", "CMPUT301@ualberta.ca", 3);
    // rider2 (with the same name as rider1)
    Account rider2 = new Account("testRiderName1", "7807101331", "CMPUT321@ualberta.ca", 3);
    //driver1
    Account driver = new Account("testDriverName1", "7807101331", "CMPUT321@ualberta.ca", 1);

    /**
     * Test for UC-P01 (US03.01.01)
     */
    @Test
    public void testCreateProfile(){
        ArrayList<Account> resultAccounts = new ArrayList<Account>();
        Account resultAccount;
        String resultName;
        String resultPhone;
        String resultEmail;

        // add the account to the elastic search server
        ElasticsearchAccountController.AddAccountTask addAccountTask =
                new ElasticsearchAccountController.AddAccountTask();
        addAccountTask.execute(rider1);

        // test if the profile is created
        ElasticsearchAccountController.GetAccountTask getAccountTask =
                new ElasticsearchAccountController.GetAccountTask();
        getAccountTask.execute("testRiderName1");
        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get accounts from the elastic search", false);
        }
        resultAccount = resultAccounts.get(0);
        resultName = resultAccount.getUsername();
        assertTrue("name should be the same", resultName.equals("testRiderName1"));
        resultPhone = resultAccount.getPhone();
        assertTrue("phone should be the same", resultPhone.equals("7807101111"));
        resultEmail = resultAccount.getEmail();
        assertTrue("email should be the same", resultEmail.equals("CMPUT301@ualberta.ca"));

        // create a user with a same username
        // add the account to the elastic search server
        addAccountTask.execute(rider2);

        // test if the profile is created
        getAccountTask.execute("testRiderName1");
        // check the if the username has been registered already
        getAccountTask.execute("testRiderName1");
        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get accounts from the elastic search", false);
        }
        assertTrue("the user with a same username shouldn't be added to the database",
                resultAccounts.size() == 2);
    }

    /**
     * Test for UC-P02 (US03.02.01)
     */
    @Test
    public void testEditProfile(){
        ArrayList<Account> resultAccounts = new ArrayList<Account>();
        Account resultAccount;
        // add the account to the elastic search server
        ElasticsearchAccountController.AddAccountTask addAccountTask =
                new ElasticsearchAccountController.AddAccountTask();
        addAccountTask.execute(rider1);

        // test if the profile is created
        ElasticsearchAccountController.GetAccountTask getAccountTask =
                new ElasticsearchAccountController.GetAccountTask();
        getAccountTask.execute("testRiderName1");
        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get accounts from the elastic search", false);
        }
        resultAccount = resultAccounts.get(0);

        // edit profile
        resultAccount.setPhone("7807108888");
        resultAccount.setEmail("TESTEMAIL@ualberta.ca");

        // add the account to the elastic search server again
        addAccountTask.execute(rider1);
        //get it again
        getAccountTask.execute("testRiderName1");
        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get accounts from the elastic search", false);
        }
        resultAccount = resultAccounts.get(0);


        // test if the profile is edited
        String phoneNumber = resultAccount.getPhone();
        String email = resultAccount.getEmail();
        assertTrue("phone number doesn't edited correctly", phoneNumber.equals("7807108888"));
        assertTrue("email doesn't edited correctly", email.equals("TESTEMAIL@ualberta.ca"));
    }

    /**
     * Test for UC-P03 (US03.03.01)
     */
    @Test
    public void testViewProfile(){
        ArrayList<Account> resultAccounts = new ArrayList<Account>();
        Account resultAccount;
        // add the account to the elastic search server
        ElasticsearchAccountController.AddAccountTask addAccountTask =
                new ElasticsearchAccountController.AddAccountTask();
        addAccountTask.execute(rider1);

        // test if the profile is created
        ElasticsearchAccountController.GetAccountTask getAccountTask =
                new ElasticsearchAccountController.GetAccountTask();
        getAccountTask.execute("testRiderName1");
        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get accounts from the elastic search", false);
        }
        resultAccount = resultAccounts.get(0);

        // test if the system can get the profile information
        String username = resultAccount.getUsername();
        String phoneNumber = resultAccount.getPhone();
        String email = resultAccount.getEmail();

        assertTrue("username doesn't display correctly", username.equals("testUserName"));
        assertTrue("phone number doesn't display correctly", phoneNumber.equals("7807109999"));
        assertTrue("email doesn't edited display", email.equals("CMPUT301@ualberta.ca"));
    }

    /**
     * Test for UC-P02-01 (US03.04.01)
     */
    @Test
    public void testEditProfileAboutVehicle(){
        ArrayList<Account> resultAccounts = new ArrayList<Account>();
        Account resultAccount;
        String vehicleInfo = "test vehicle info";
        String resultInfo;

        // add the account to the elastic search server
        ElasticsearchAccountController.AddAccountTask addAccountTask =
                new ElasticsearchAccountController.AddAccountTask();
        addAccountTask.execute(driver);

        // test if the profile is created
        ElasticsearchAccountController.GetAccountTask getAccountTask =
                new ElasticsearchAccountController.GetAccountTask();
        getAccountTask.execute("testDriverName1");
        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get accounts from the elastic search", false);
        }
        resultAccount = resultAccounts.get(0);
        resultAccount.setVehicleInfo(vehicleInfo);
        resultInfo = resultAccount.getUsername();
        assertTrue("vehicle info should be the same", resultInfo.equals(vehicleInfo));

    }
}
