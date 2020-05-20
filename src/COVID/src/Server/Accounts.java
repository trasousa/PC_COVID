package COVID.src.Server;

import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.PasswordExceptions.MismatchPassException;
import COVID.src.Server.Exceptions.InvalidUsernameServer;

import java.util.HashMap;
import java.util.Map;
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
            Account account = accounts.get(id);
            account.lockAccount();
            lockAccounts.unlock();
            if(account.getPasswd().equals(passwd)){
                account.unlockAccount();
            }
            else {
                account.unlockAccount();
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
    public float updateCases(String id, int cases) throws InvalidAcount {
        lockAccounts.lock();
        accounts.forEach(
                (s,account) -> {
                    account.lockAccount();
                });
        lockAccounts.unlock();
        float newEstimate = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            newEstimate += account.getCases()/150;
            account.unlockAccount();
        }
        newEstimate = newEstimate/(accounts.size());
        return newEstimate;
    }
}
