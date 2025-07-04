package test;

import model.Loan;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {
    // Тестовые данные
    private final int testBookId = 1;
    private final int testUserId = 1;
    private final LocalDate testLoanDate = LocalDate.now();
    private final LocalDate testReturnDate = LocalDate.now().plusDays(10);
    private final LocalDate testPastDate = LocalDate.now().minusDays(5);

    @Test
    void testLoanCreation() {
        Loan loan = new Loan(testBookId, testUserId, testLoanDate);

        assertEquals(testBookId, loan.getBookId());
        assertEquals(testUserId, loan.getUserId());
        assertEquals(testLoanDate, loan.getLoanDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    void testLoanCreationLoanDateIsNull() {
        assertThrows(NullPointerException.class, () -> new Loan(testBookId, testUserId, null));
    }

    @Test
    void testSetReturnDate() {
        Loan loan = new Loan(testBookId, testUserId, testLoanDate);
        loan.setReturnDate(testReturnDate);

        assertEquals(testReturnDate, loan.getReturnDate());
    }

    @Test
    void testSetReturnDateBeforeLoanDate() {
        Loan loan = new Loan(testBookId, testUserId, testLoanDate);

        assertThrows(IllegalArgumentException.class, () -> loan.setReturnDate(testPastDate));
    }

    @Test
    void testSetReturnDateIsNull() {
        Loan loan = new Loan(testBookId, testUserId, testLoanDate);

        assertThrows(NullPointerException.class, () -> loan.setReturnDate(null));
    }

    @Test
    void testNotOverdueReturnedBook() {
        Loan loan = new Loan(testBookId, testUserId, testLoanDate);
        loan.setReturnDate(testReturnDate);

        assertFalse(loan.isOverdue());
    }

    @Test
    void testNotOverdue() {
        Loan loan = new Loan(testBookId, testUserId, LocalDate.now().minusDays(20));

        assertFalse(loan.isOverdue());
    }

    @Test
    void testIsOverdue() {
        Loan loan = new Loan(testBookId, testUserId, LocalDate.now().minusDays(31));

        assertTrue(loan.isOverdue());
    }
}