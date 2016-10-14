package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by administrator on 2016-10-14.
 */
public class ProfileTest extends ActivityInstrumentationTestCase2 {

    public ProfileTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }

    /**
     * Test for UC-P01 (US03.01.01)
     */
    public void testCreateProfile(){
        UserList testUserList = new UserList();
        Profile testProfile1 = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
        UserAccount user1 = new UserAccount(testProfile1);
        testUserList.add(user1);

        // test if the profile is created
        UserAccount testUserAccount = testUserList.getUser("testUserName");
        Profile profile = testUserAccount.getProfile();
        assertTrue("profile should be the same", testProfile1.equals(profile));

        // create a user with a same username
        Profile testProfile2 = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
        UserAccount user2 = new UserAccount(testProfile2);
        testUserList.add(user2);
        assertTrue("the user with a same username shouldn't be added to the database",
                testUserList.size()==1);
    }

    /**
     * Test for UC-P02 (US03.02.01)
     */
    public void testEditProfile(){
        UserList testUserList = new UserList();
        Profile testProfile1 = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
        UserAccount user1 = new UserAccount(testProfile1);
        testUserList.add(user1);

        // edit profile
        testUser = testUserList.getUser("testUserName")
        Profile testProfile = testUser.getProfile();
        testProfile.setUsername("newTestUserName");
        testProfile.setPhoneNumber("7807108888");
        testProfile.setEmail("TESTEMAIL@ualberta.ca");

        // test if the profile is edited
        String username = testProfile.getUsername();
        String phoneNumber = testProfile.getPhoneNumber();
        String email = testProfile.getEmail();
        assertTrue("username doesn't edited correctly", username.equals("testUserName"));
        assertTrue("phone number doesn't edited correctly", phoneNumber.equals("7807108888"));
        assertTrue("email doesn't edited correctly", email.equals("TESTEMAIL@ualberta.ca"));
    }

    /**
     * Test for UC-P03 (US03.03.01)
     */
    public void testViewProfile(){
        UserList testUserList = new UserList();
        Profile testProfile1 = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
        UserAccount user1 = new UserAccount(testProfile1);
        testUserList.add(user1);

        // test if the system can get the profile information
        String username = testProfile.getUsername();
        String phoneNumber = testProfile.getPhoneNumber();
        String email = testProfile.getEmail();

        assertTrue("username doesn't display correctly", username.equals("testUserName"));
        assertTrue("phone number doesn't display correctly", phoneNumber.equals("7807109999"));
        assertTrue("email doesn't edited display", email.equals("CMPUT301@ualberta.ca"));
    }
}
