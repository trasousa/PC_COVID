package COVID.src.Exceptions.PasswordExceptions;

import COVID.src.Exceptions.PasswordException;

public class InvalidPasswordException extends PasswordException {
    public InvalidPasswordException(String error)
    {
        super(error);
    }
}