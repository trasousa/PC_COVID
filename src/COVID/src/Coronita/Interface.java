package COVID.src.Coronita;

import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

public interface Interface {

        public void registerClient(String id, String pass1,String pass2)
                throws AccountException, PasswordException, CoronitaRemotException;
        public void authenticate(String id,String password)
                throws AccountException, PasswordException, CoronitaRemotException;
        public void removeClient(String id, String password)
                throws AccountException, PasswordException;
        public void updateEstimate(int cases)
                throws InvalidNumCases, AccountException;


}

