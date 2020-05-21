package COVID.src.Exceptions.AccountExceptions;

import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.PasswordException;

public class MismatchPassException extends AccountException{
    public MismatchPassException(String error) {
        super(error);
    }
}
