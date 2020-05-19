package COVID.src.Exceptions.AccountExceptions;

import COVID.src.Exceptions.AccountException;

public class InvalidUsername extends AccountException {
    public InvalidUsername(String error) {
        super(error);
    }
}
