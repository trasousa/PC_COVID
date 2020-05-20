package COVID.src.Exceptions.AccountExceptions;

import COVID.src.Exceptions.AccountException;

public class InvalidAccount extends AccountException {
    public InvalidAccount(String error) {
        super(error);
    }
}
