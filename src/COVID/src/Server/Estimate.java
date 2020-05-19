package COVID.src.Server;

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
    public Estimate(){
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

    public void updateEstimate(float newEstimate){
        lockCasos.lock();
        estimate = newEstimate; //substituir pela forma de calcular casos
        updated.clear();
        update.signalAll();
        lockCasos.unlock();
    }
}
