package Server.DataStructures;

import Exceptions.AccountExceptions.InvalidAccount;
import Exceptions.AccountExceptions.MismatchPassException;
import Server.Exceptions.InvalidAccountServer;
import Server.Exceptions.InvalidUsernameServer;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Accounts {
    HashMap<String,Account> accounts;
    ReentrantLock lock;

    public Accounts(){
        accounts = new HashMap<String, Account>();
        lock = new ReentrantLock();
        accounts.put("admin",new Account("admin1"));
    }

    public void lockAccounts(){
        lock.lock();
    }

    public void unlockAccounts(){
        lock.unlock();
    }

    public void checkUsername(String id) throws InvalidUsernameServer {
        lockAccounts();
        if (!(accounts.containsKey(id))) {
            ;
        }
        else{
            unlockAccounts();
            throw new InvalidUsernameServer(id);
        }
        unlockAccounts();
    }

    public void addAccount(String id, String passwd){
        lockAccounts();
        accounts.put(id,new Account(passwd));
        unlockAccounts();
    }

    public void checkPasswd(String id, String passwd) throws InvalidAccount,MismatchPassException {
        lockAccounts();
        if (accounts.containsKey(id)){
            Account account = accounts.get(id);
            account.lockAccount();
            unlockAccounts();
            if(account.getPasswd().equals(passwd)){
                account.unlockAccount();
            }
            else {
                account.unlockAccount();
                throw new MismatchPassException("Wrong password");
            }
        }

        else {
            unlockAccounts();
            throw new InvalidAccountServer(id);
        }

    }

    public int setCountry(String id, String country){
        int cases;
        lockAccounts();
        Account account = accounts.get(id);
        account.lockAccount();
        unlockAccounts();
        account.setCountry(country);
        cases = account.getCases();
        account.unlockAccount();
        return cases;
    }

    public boolean hasReport(String id,String country){
        Account account;
        boolean hasReport;
        lockAccounts();
        account = accounts.get(id);
        account.lockAccount();
        unlockAccounts();
        hasReport = (account.hasReport(country));
        account.unlockAccount();
        return hasReport;
    }

    public float updateCases(String id, int cases){
        float newEstimate;
        int diffCases;
        lockAccounts();
        Account account = accounts.get(id);
        account.lockAccount();
        unlockAccounts();
        diffCases = account.setCases(cases);
        account.unlockAccount();
        newEstimate = ((float) diffCases)/150;
        return newEstimate;
    }

    public void removeAccount(String id, String passwd) throws MismatchPassException{
        lockAccounts();
        if(accounts.get(id).getPasswd().equals(passwd)){
            accounts.remove(id);
            unlockAccounts();
        }
        else {
            unlockAccounts();
            throw new MismatchPassException("Wrong password");
        }
    }

}
