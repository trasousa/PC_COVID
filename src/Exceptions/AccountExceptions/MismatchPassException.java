package Exceptions.AccountExceptions;

import Exceptions.AccountException;

public class MismatchPassException extends AccountException{
    public MismatchPassException(String error) {
        super(error);
    }
}
