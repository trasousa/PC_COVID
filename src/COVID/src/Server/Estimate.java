package COVID.src.Server;

import COVID.src.Server.Account;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Estimate {
    float estimate;
    HashSet<String> updated;
    ReentrantLock lockCasos;
    Condition update;
    Accounts accounts;
    public Estimate(ConcurrentHashMap<String, Account> accounts){
        estimate = 0;
        updated = new HashSet<String>();
        lockCasos = new ReentrantLock();
        update = lockCasos.newCondition();
    }
    public float getEstimate(String id) throws InterruptedException {
        float estimateNow;
        lockCasos.lock();
        while(updated.contains(id)){
            update.await();
        }
        estimateNow = estimate;
        lockCasos.unlock();
        return estimateNow;
    }

    public void updateEstimate(String id, int newCases){
        float newEstimate = 0;
        lockCasos.lock();
        accounts.updateCases(id,newCases);
        updated.clear();
        update.signalAll();
        lockCasos.unlock();
    }
}
