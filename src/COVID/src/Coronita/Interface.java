package COVID.src.Coronita;

import COVID.src.Exceptions.*;

public interface Interface {

        public void registerClient(String id, String pass1,String pass2)
                throws InvalidUsername, InvalidPasswordException, MismatchPassException, CoronitaRemotException;
        public void authenticate(String id,String password)
                throws InvalidUsername, InvalidPasswordException, CoronitaRemotException;
        public void removeClient(String id, String password)
                throws InvalidUsername, InvalidAcount;
        public void updateEstimate(int cases)
                throws InvalidNumCases;


}

