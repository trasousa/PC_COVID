package Coronita;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bag {

    String letter[];
    ReentrantLock lockBag;
    Boolean updated;
    Condition update;

    public Bag(){
        updated = false;
        lockBag = new ReentrantLock();
        update = lockBag.newCondition();
    }

    public String[] getLetter() {
        this.lockBag.lock();
        while(updated == false){
            try {
                update.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        updated = false;
        String myletter[] = letter;
        this.lockBag.unlock();
        return myletter;
    }

    public void putLetter(String stamp[]){
        this.lockBag.lock();
        this.letter = stamp;
        updated = true;
        update.signal();
        this.lockBag.unlock();
    }
}
