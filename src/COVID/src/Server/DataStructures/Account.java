package COVID.src.Server.DataStructures;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Account{
    ReentrantLock lock;
    String passwd;
    String currentCountry;
    HashMap<String,Integer> cases;

    public Account(String passw){
        this.passwd = passw;
        this.cases = initCases();
        lock = new ReentrantLock();
    }
    private HashMap<String,Integer> initCases(){
        HashMap<String,Integer> cases = new HashMap<String, Integer>();
        String[] countries = {"pt","es","cn","it"};
        for(String country : countries){
            cases.put(country,-1);
        }
        return cases;
    }
    public String getPasswd(){ return passwd; }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int setCases(int cases){
        int caseDiff;
        int oldCases;
        oldCases = this.cases.get(currentCountry);
        caseDiff = cases - oldCases;
        if(oldCases == -1){
            caseDiff--;
        }
        this.cases.put(currentCountry,cases);
        return caseDiff;
    }

    public boolean hasReport(String country){
        return (cases.get(country) != -1);
    }

    public int getCases(){
        int cases = this.cases.get(currentCountry);
        return cases;
    }

    public String getCountry() {
        return currentCountry;
    }

    public void setCountry(String country){
        this.currentCountry = country;
    }

    public void lockAccount(){
        lock.lock();
    }

    public void unlockAccount(){
        lock.unlock();
    }
}
