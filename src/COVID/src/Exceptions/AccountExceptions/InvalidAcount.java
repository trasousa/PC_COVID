package COVID.src.Exceptions.AccountExceptions;

import COVID.src.Exceptions.AccountException;

public class InvalidAcount extends AccountException {
    public InvalidAcount(String error) {
        super(error);
    }
}
