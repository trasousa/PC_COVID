package COVID.src.Server;

import COVID.src.Exceptions.MismatchPassException;
import COVID.src.Server.Exceptions.InvalidUsernameServer;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Accounts {
    HashMap<String,Account> accounts;
    ReentrantLock lockAccounts;
    public Accounts(){
        accounts = new HashMap<String, Account>();
        lockAccounts = new ReentrantLock();
    }

    public void addAccount(String id, String passwd) throws InvalidUsernameServer {
        lockAccounts.lock();
        if(accounts.containsKey(id)){
            throw new InvalidUsernameServer(id);
        }
        else{
            accounts.put(id,new Account(passwd ,0));
        }
    }

    public void checkPasswd(String id, String passwd) throws InvalidUsernameServer, MismatchPassException {
        lockAccounts.lock();
        if (accounts.containsKey(id)){
            if(accounts.get(id).getPasswd().equals(passwd)){
                lockAccounts.unlock();
            }
            else {
                lockAccounts.unlock();
                throw new MismatchPassException("Wrong password");
            }
        }
        else {
            lockAccounts.unlock();
            throw new InvalidUsernameServer(id);
        }
    }

    public void removeClient(String id, String passwd) throws MismatchPassException, InvalidUsernameServer {
        lockAccounts.lock();
        if(accounts.containsKey(id)){
            if(accounts.get(id).getPasswd().equals(passwd)){
                accounts.remove(id);
            }
            else {
                lockAccounts.unlock();
                throw new MismatchPassException("Wrong password");
            }
        }

        else {
            lockAccounts.unlock();
            throw new InvalidUsernameServer(id);
        }
    }
}
