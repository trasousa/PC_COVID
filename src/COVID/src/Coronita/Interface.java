package COVID.src.Coronita;

import COVID.src.Exceptions.CoronitaRemotException;
import COVID.src.Exceptions.InvalidAcount;
import COVID.src.Exceptions.InvalidNumCases;
import COVID.src.Exceptions.InvalidUsername;
import COVID.src.Exceptions.InvalidPasswordException;

public interface Interface {

        double CreateAcount(String Username, String pass)

        public void registerClient(String id, String passwd);
        public void removeClient(String id) throws InvalidAcount;
        public void authenticate(String id,String passwd) throws InvalidAcount;
        public void updateEstimate(int cases);


}

