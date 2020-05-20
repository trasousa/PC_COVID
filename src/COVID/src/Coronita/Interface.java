package COVID.src.Coronita;

import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

import java.io.IOException;

public interface Interface {

        public void registerAccount(String id, String pass1,String pass2)
                throws AccountException, PasswordException, CoronitaRemotException, IOException;
        public void authenticate(String id,String password)
                throws AccountException, PasswordException, CoronitaRemotException, IOException;
        public void removeAccount(String id, String password)
                throws AccountException, PasswordException, CoronitaRemotException, IOException;
        public void updateEstimate(int cases)
                throws InvalidNumCases, AccountException;


}

