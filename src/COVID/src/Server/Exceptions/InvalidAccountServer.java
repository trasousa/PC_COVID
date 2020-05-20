package COVID.src.Server.Exceptions;

import COVID.src.Exceptions.AccountException;
import COVID.src.Exceptions.AccountExceptions.InvalidAcount;

public class InvalidAccountServer extends InvalidAcount {
    public InvalidAccountServer(String error) {
        super(error);
    }
}
