package COVID.src.Server;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Estimates{
    HashMap<String,Estimate> estimates;
    Estimate globalEstimate;
    ReentrantLock lock;
    public Estimates(){
        globalEstimate = new Estimate();
        estimates = initEstimates();
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

    public Estimate getEstimate(String country){
        Estimate estimate;
        lockEstimates();
        estimate = estimates.get(country);
        unlockEstimates();
        return estimate;
    }

}
