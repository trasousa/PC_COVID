package Server.Exceptions;

import Exceptions.AccountExceptions.InvalidAccount;

public class InvalidAccountServer extends InvalidAccount {
    public InvalidAccountServer(String id) {
        super("A conta " + id + "já existe");
    }
}
