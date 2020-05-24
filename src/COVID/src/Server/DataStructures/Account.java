package COVID.src.Server.DataStructures;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
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
            cases.put(country,0);
        }
        return cases;
    }
    public String getPasswd(){ return passwd; }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCases(int cases){
        this.cases.replace(currentCountry,cases);
    }

    public int getCases(){
        int cases = this.cases.get(currentCountry);
        return cases;
    }

    public String getCountry() {
        return currentCountry;
    }

    public void setCountry(String country) {
        this.currentCountry = country;
    }

    public void lockAccount(){
        lock.lock();
    }

    public void unlockAccount(){
        lock.unlock();
    }
}
