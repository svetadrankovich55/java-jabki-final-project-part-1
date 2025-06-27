package exception;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String entityType) {
        super("По данному запросу " + entityType + " не найдено");
    }
}