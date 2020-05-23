package COVID.src.Server;

import COVID.src.Exceptions.AccountExceptions.InvalidAccount;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Estimate {
    float estimate;
    int reporters;
    HashSet<String> updated;
    ReentrantLock lockCasos;
    Condition update;

    public Estimate(){
        estimate = 0;
        updated = new HashSet<String>();
        lockCasos = new ReentrantLock();
        update = lockCasos.newCondition();
    }
    public void lockEstimate(){
        lockCasos.lock();
    }
    public void unlockEstimate(){
        lockCasos.unlock();
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

    public void update(float newEstimate){
        lockCasos.lock();
        estimate = newEstimate;
        updated.clear();
        update.signalAll();
        lockCasos.unlock();
    }
}
