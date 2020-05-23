package COVID.src.Server;

import java.util.HashMap;

public class Estimates{
    HashMap<String,Estimate> estimates;
    Estimate globalEstimate;

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
}
