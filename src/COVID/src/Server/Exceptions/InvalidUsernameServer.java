package COVID.src.Server.Exceptions;

import COVID.src.Exceptions.InvalidUsername;
import COVID.src.Server.Exceptions.*;
public class InvalidUsernameServer extends InvalidUsername {
    public InvalidUsernameServer(String clientId){
        super("A conta " + clientId + "não existe");
    }
}
