package Server.DataStructures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Estimates{
    HashMap<String,Estimate> estimates;
    float globalEstimate;
    HashSet<String> updated;
    ReentrantLock lock;
    Condition update;
    public Estimates(){
        globalEstimate = 0;
        estimates = initEstimates();
        updated = new HashSet<String>();
        lock = new ReentrantLock();
        update = lock.newCondition();
    }

    private HashMap<String,Estimate> initEstimates(){
        HashMap<String,Estimate> estimates = new HashMap<String, Estimate>();
        String[] countries = {"pt","es","cn","it"};
        for(String country : countries){
            estimates.put(country,new Estimate());
        }
        return estimates;
    }

    public void lockEstimates(){
        lock.lock();
    }

    public void unlockEstimates(){
        lock.unlock();
    }

    public void waitUpdate(String id){
        lockEstimates();
        while(updated.contains(id)){
            try {
                update.await();
            } catch (InterruptedException e) {
                System.out.println("Error while waiting for updates");
            }
        }
        unlockEstimates();
    }

    public Pair<Float, Float> getEstimate(String id, String country){
        Estimate countryEstimate;
        float globalUpdate;
        float countryUpdate = -1;
        lockEstimates();
        updated.add(id);
        globalUpdate = globalEstimate;
        countryEstimate = estimates.get(country);
        countryEstimate.lockEstimate();
        unlockEstimates();
        if(!(countryEstimate.isUpdated(id))){
            countryUpdate = countryEstimate.getEstimate(id);
        }
        countryEstimate.unlockEstimate();
        return new Pair<Float,Float>(globalUpdate,countryUpdate);
    }

    public void firstUpdate(String country,float newEstimate){
        Estimate estimate;
        float estimateDiff;
        lockEstimates();
        estimate = estimates.get(country);
        estimateDiff = estimate.firstUpdate(newEstimate);
        globalEstimate += (estimateDiff/4);
        updated.clear();
        update.signalAll();
        unlockEstimates();
    }

    public void update(String country, float newEstimate){
        Estimate estimate;
        float estimateDiff;
        lockEstimates();
        estimate = estimates.get(country);
        estimateDiff = estimate.update(newEstimate);
        globalEstimate += estimateDiff/4;
        updated.clear();
        update.signalAll();
        unlockEstimates();
    }

    public void trigger(String id,String country){
        lockEstimates();
        updated.remove(id);
        update.signalAll();
        estimates.get(country).remove(id);
        unlockEstimates();
    }
}
