package COVID.src.Exceptions.PasswordExceptions;

import COVID.src.Exceptions.PasswordException;

public class MismatchPassException extends PasswordException {
    public MismatchPassException(String error) {
        super(error);
    }
}
