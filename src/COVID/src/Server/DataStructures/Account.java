package COVID.src.Server;

import java.util.concurrent.locks.ReentrantLock;

public class Account {
    ReentrantLock lock;
    String passwd;
    String country;
    int cases;

    public Account(String passw, int cases){
        this.passwd = passw;
        this.cases = 0;
        lock = new ReentrantLock();
    }

    public String getPasswd() { return passwd; }

    public int getCases() {
        return cases;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void lockAccount(){
        lock.lock();
    }

    public void unlockAccount(){
        lock.unlock();
    }
}
