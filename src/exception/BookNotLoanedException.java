package exception;

public class BookNotLoanedException extends Exception{
    public BookNotLoanedException() {
        super("Эта книга не была выдана этому пользователю");
    }
}