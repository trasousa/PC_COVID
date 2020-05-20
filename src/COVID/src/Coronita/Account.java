package COVID.src.Coronita;

import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private String id;
    private String passwd;
    private int cases;
    private ReentrantLock lockAccount;

    public Account(String id, String passwd) {
        this.passwd = passwd;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPasswd() {
        return passwd;
    }

    public int getCases() {
        return cases;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public void lock(){
        this.lockAccount.lock();
    }

    public void unlock(){
        this.lockAccount.unlock();
    }

}
