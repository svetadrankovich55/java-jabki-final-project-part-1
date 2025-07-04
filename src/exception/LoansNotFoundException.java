package exception;

public class LoansNotFoundException extends RuntimeException {
    public LoansNotFoundException() {
        super("По заданным критериям выдачи не найдены.");
    }
}