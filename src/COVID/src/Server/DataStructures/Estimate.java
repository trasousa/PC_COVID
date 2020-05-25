package COVID.src.Server.DataStructures;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Estimate {
    float estimate;
    int reports;
    HashSet<String> updated;
    ReentrantLock lock;
    Condition update;

    public Estimate(){
        estimate = 0;
        reports = 0;
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

    public void addReport(){ this.reports++;}

    public float getEstimate(String id){
        float estimateNow;
        lockEstimate();
        updated.add(id);
        estimateNow = estimate;
        unlockEstimate();
        return estimateNow;
    }

    public void update(float newEstimate){
        lockEstimate();
        estimate += newEstimate/reports;
        updated.clear();
        update.signalAll();
        unlockEstimate();
    }

    public boolean isUpdated(String id){
        lockEstimate();
        boolean isUpdated;
        isUpdated = updated.contains(id);
        unlockEstimate();
        return isUpdated;
    }

    public void remove(String id){
        updated.remove(id);
    }
}