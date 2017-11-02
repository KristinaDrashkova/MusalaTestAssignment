package exeptions;

public class FileNotFoundCustomException extends Exception {
    public FileNotFoundCustomException(String message) {
        super(message);
    }
}
