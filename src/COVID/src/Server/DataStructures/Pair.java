package COVID.src.Server.DataStructures;

public class Pair<F,S> {
    F fst;
    S snd;
    public Pair(F fst,S snd){
        this.fst = fst;
        this.snd = snd;
    }

    public F getFst(){
        return this.fst;
    }

    public S getSnd(){
        return this.snd;
    }
}
