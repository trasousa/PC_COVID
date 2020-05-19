package COVID.src.Server;

import COVID.src.abstractAccount;

import java.util.concurrent.locks.ReentrantLock;

public class Account extends abstractAccount {
    ReentrantLock lock;
    public Account(String passw,int cases) {
        super(passw,cases);
        lock = new ReentrantLock();
    }
    public void lockAccount(){
        lock.lock();
    }
    public void unlockAccount(){
        lock.unlock();
    }
}
