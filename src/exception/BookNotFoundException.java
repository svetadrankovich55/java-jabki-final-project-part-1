package exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String entityType) {
        super("По данному запросу " + entityType + " не найдено");
    }
}