package exception;

public class BookNotAvailableException extends Exception {
    public BookNotAvailableException() {
        super("Нет доступных копий для этой книги");
    }
}