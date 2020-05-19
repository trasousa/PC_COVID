package COVID.src.Exceptions;

public class InvalidUsername extends Throwable {
    public InvalidUsername(String error) {
        super(error);
    }
}
