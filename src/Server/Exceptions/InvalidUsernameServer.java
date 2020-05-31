package Server.Exceptions;

import Exceptions.AccountExceptions.InvalidUsername;

public class InvalidUsernameServer extends InvalidUsername {
    public InvalidUsernameServer(String clientId){
        super("A conta " + clientId + " não existe");
    }
}
