package COVID.src.Exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String error)
    {
        super(error);
    }
}