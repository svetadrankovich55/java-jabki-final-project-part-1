package exception;

public class OverdueLoansNotFoundException extends RuntimeException {
    public OverdueLoansNotFoundException() {
        super("Нет просроченных выдач.");
    }
}