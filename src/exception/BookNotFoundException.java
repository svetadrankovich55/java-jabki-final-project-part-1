package exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("По данному запросу книг не найдено");
    }
}