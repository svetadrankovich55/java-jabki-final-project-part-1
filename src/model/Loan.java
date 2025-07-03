package model;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {

    private static final int RETURN_PERIOD = 30;

    private final int bookId;
    private final int userId;
    private final LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(int bookId, int userId, LocalDate loanDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = Objects.requireNonNull(loanDate, "Дата выдачи книги должна быть заполнена");
        this.returnDate = null;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Дата возврата книги должна быть больше даты выдачи книги");
        }
        if (returnDate == null) {
            throw new NullPointerException("Дата возврата не может быть пустой");
        }
        this.returnDate = returnDate;
    }

    public boolean isOverdue() {
        if (returnDate != null) {
            return false;
        }
        return loanDate.plusDays(RETURN_PERIOD).isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return bookId == loan.bookId && userId == loan.userId && Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userId, loanDate);
    }
}