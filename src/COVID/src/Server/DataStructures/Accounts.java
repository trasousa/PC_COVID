package COVID.src.Server.DataStructures;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;
import COVID.src.Exceptions.AccountExceptions.MismatchPassException;
import COVID.src.Server.Exceptions.InvalidAccountServer;
import COVID.src.Server.Exceptions.InvalidUsernameServer;

import java.util.HashMap;
import java.util.Map;
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
                ;
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

    public void removeAccount(String id, String passwd) throws MismatchPassException,InvalidAccountServer{
        lockAccounts();
        if(accounts.containsKey(id)){
            if(accounts.get(id).getPasswd().equals(passwd)){
                accounts.remove(id);
                unlockAccounts();
            }
            else {
                unlockAccounts();
                throw new MismatchPassException("Wrong password");
            }
        }

        else {
            unlockAccounts();
            throw new InvalidAccountServer(id);
        }
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
        //accounts.forEach(
        //        (s,a) -> {
        //            a.lockAccount();
        //        });
        //unlockAccounts();
        //float newEstimate = 0;
        //for (Map.Entry<String, Account> entry : accounts.entrySet()) {
        //    Account account = entry.getValue();
        //    newEstimate += ((float) account.getCases())/150.0;
        //    account.unlockAccount();
        //}
        return newEstimate;
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
}
