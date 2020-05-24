package COVID.src.Server;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Estimate {
    float estimate;
    int reporters;
    HashSet<String> updated;
    ReentrantLock lock;
    Condition update;

    public Estimate(){
        estimate = 0;
        updated = new HashSet<String>();
        lock = new ReentrantLock();
        update = lock.newCondition();
    }
    public void lockEstimate(){
        lock.lock();
    }
    public void unlockEstimate(){
        lock.unlock();
    }
    public float getEstimate(String id) throws InterruptedException {
        float estimateNow;
        lockEstimate();
        while(updated.contains(id)){
            update.await();
        }
        updated.add(id);
        estimateNow = estimate;
        unlockEstimate();
        return estimateNow;
    }

    public void update(float newEstimate){
        lockEstimate();
        estimate = newEstimate;
        updated.clear();
        update.signalAll();
        unlockEstimate();
    }

    public void trigger(){
        lockEstimate();
        update.signalAll();
        unlockEstimate();
    }
}