package COVID.src.Exceptions;

public class InvalidPasswordException extends PasswordException{
    public InvalidPasswordException(String error)
    {
        super(error);
    }
}