package COVID.src.Server.Exceptions;

import COVID.src.Exceptions.AccountExceptions.InvalidUsername;

public class InvalidUsernameServer extends InvalidUsername {
    public InvalidUsernameServer(String clientId){
        super("A conta " + clientId + "não existe");
    }
}
