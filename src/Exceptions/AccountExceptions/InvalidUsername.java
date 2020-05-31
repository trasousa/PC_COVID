package Exceptions.AccountExceptions;

import Exceptions.AccountException;

public class InvalidUsername extends AccountException {
    public InvalidUsername(String error) {
        super(error);
    }
}
