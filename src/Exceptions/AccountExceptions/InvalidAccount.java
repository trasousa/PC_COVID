package Exceptions.AccountExceptions;

import Exceptions.AccountException;

public class InvalidAccount extends AccountException {
    public InvalidAccount(String error) {
        super(error);
    }
}
