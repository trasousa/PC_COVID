package COVID.src.Coronita;

import COVID.src.Exceptions.*;
import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

import java.io.IOException;

public interface Interface {

        public void registerAccount(String id, String password)
                throws AccountException;
        public void authenticate(String id,String password)
                throws AccountException;
        public void removeAccount(String id, String password)
                throws AccountException;
        public void updateEstimate(int cases);
        public void checkUsername(String Username)
                throws InvalidUsername;
}

