package Coronita;

import Exceptions.AccountException;
import Exceptions.AccountExceptions.InvalidUsername;

public interface Interface {

        public void registerAccount(String id, String password);
        public void authenticate(String id,String password)
                throws AccountException;
        public void removeAccount(String id, String password)
                throws AccountException;
        public void updateEstimate(int cases);
        public void checkUsername(String Username)
                throws InvalidUsername;
        public int setCountry(String country);
        public void logout();
}

