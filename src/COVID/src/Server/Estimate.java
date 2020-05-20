package COVID.src.Server;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Estimate {
    float estimate;
    HashSet<String> updated;
    ReentrantLock lockCasos;
    Condition update;
    Accounts accounts;
    public Estimate(Accounts accounts){
        estimate = 0;
        updated = new HashSet<String>();
        lockCasos = new ReentrantLock();
        update = lockCasos.newCondition();
        this.accounts = accounts;
    }
    public float getEstimate(String id) throws InterruptedException {
        float estimateNow;
        lockCasos.lock();
        while(updated.contains(id)){
            update.await();
        }
        updated.add(id);
        estimateNow = estimate;
        lockCasos.unlock();
        return estimateNow;
    }

    public void update(String id, int newCases) throws InvalidAccount {
        lockCasos.lock();
        accounts.updateCases(id,newCases);
        updated.clear();
        update.signalAll();
        lockCasos.unlock();
    }
}
