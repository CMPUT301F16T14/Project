package ca.ualberta.cs.linkai.beep;

/**
 * Created by lincolnqi on 2016-11-12.
 */

public class RuntimeAccount {
    private static RuntimeAccount instance = null;
    private Account myAccount;

    private RuntimeAccount(Account account){
        myAccount = account;
    }
    private Account getAccount(){
        return myAccount;
    }

    public static RuntimeAccount getInstance(){
        if (instance == null){
            instance = new RuntimeAccount(WelcomeActivity.logInAccount);
        }
        return  instance;
    }
}

