package assignment.synonymRegister.exceptions;


public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
