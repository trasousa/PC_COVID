package COVID.src.Server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Cases {
    int cases;
    HashSet<String> updated;
    ReentrantLock lockCasos;
    Condition update;
    public Cases(){
        cases = 0;
        updated = new HashSet<String>();
        lockCasos = new ReentrantLock();
        update = lockCasos.newCondition();
    }
    public int getCases(String id) throws InterruptedException {
        int casesNow;
        lockCasos.lock();
        while(updated.contains(id)){
            update.await();
        }
        casesNow = cases;
        lockCasos.unlock();
        return casesNow;
    }



    public void addCases(int newCases){
        lockCasos.lock();
        cases += newCases;
        updated.clear();
        update.signalAll();
        lockCasos.unlock();
    }
}
