package Exceptions.PasswordExceptions;

import Exceptions.PasswordException;

public class InvalidPasswordException extends PasswordException {
    public InvalidPasswordException(String error)
    {
        super(error);
    }
}