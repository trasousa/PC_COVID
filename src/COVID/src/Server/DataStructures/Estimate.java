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

    public Estimate(){
        estimate = 0;
        reports = 0;
        updated = new HashSet<String>();
        lock = new ReentrantLock();
    }
    public void lockEstimate(){
        lock.lock();
    }
    public void unlockEstimate(){
        lock.unlock();
    }

    public float getEstimate(String id){
        float estimateNow;
        lockEstimate();
        updated.add(id);
        estimateNow = estimate;
        unlockEstimate();
        return estimateNow;
    }

    public float firstUpdate(float newEstimate){
        float oldEstimate;
        float estimateDiff;
        lockEstimate();
        reports ++;
        oldEstimate = estimate;
        estimate += (newEstimate-estimate)/reports;
        updated.clear();
        estimateDiff = estimate-oldEstimate;
        unlockEstimate();
        return estimateDiff;
    }

    public float update(float newEstimate){
        float oldEstimate;
        float estimateDiff;
        lockEstimate();
        oldEstimate = estimate;
        estimate += newEstimate/reports;
        updated.clear();
        estimateDiff = estimate-oldEstimate;
        unlockEstimate();
        return estimateDiff;
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