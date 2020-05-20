package COVID.src.Coronita;


import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;
import COVID.src.Exceptions.PasswordExceptions.MismatchPassException;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;


public class CoronitaServer implements  Interface{

    private HashMap<String,Account> Accounts;
    private ReentrantLock lockCoronita;

    public CoronitaServer(){
        this.lockCoronita = new ReentrantLock();
        this.Accounts = new HashMap<>();
}
    public void registerAccount(String Username, String pass1, String pass2)
            throws InvalidUsername, PasswordException{
        if(pass1.equals(pass2)){
            this.lockCoronita.lock();
            this.Accounts.put(Username,new Account(Username,pass1));
            this.lockCoronita.unlock();
        }
        else throw new MismatchPassException("Password do not match");
    }

    public void authenticate(String Username, String password) throws InvalidAcount{
            this.lockCoronita.lock();
            if(!this.Accounts.containsKey(Username) || !this.Accounts.get(Username).equals(password)){
                this.lockCoronita.unlock();
                throw new InvalidAcount("Username or Password are incorrect");
            }
            this.lockCoronita.unlock();
    }

    public void removeAccount(String Username, String password) throws InvalidAcount{
        this.lockCoronita.lock();
        if(!this.Accounts.containsKey(Username) || !this.Accounts.get(Username).equals(password)){
            this.lockCoronita.unlock();
            throw new InvalidAcount("Username or Password are incorrect");
        }
        this.Accounts.remove(Username);
        this.lockCoronita.unlock();
    }

    public void updateEstimate(int cases) throws InvalidNumCases{
        this.lockCoronita.lock();

    }
}


