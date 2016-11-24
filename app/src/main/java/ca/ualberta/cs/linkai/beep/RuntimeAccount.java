package ca.ualberta.cs.linkai.beep;

/**
 * Created by lincolnqi on 2016-11-12.
 */

public class RuntimeAccount {
    private static RuntimeAccount instance = null;
    Account myAccount;

    private RuntimeAccount(Account account){
        myAccount = account;
    }

    public static RuntimeAccount getInstance(){
        if (instance == null){
            instance = new RuntimeAccount(WelcomeActivity.logInAccount);
        }
        return  instance;
    }
}

