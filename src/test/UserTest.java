package test;

import model.Loan;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    @BeforeEach
    void resetCounter() throws Exception {
        TestUtils.resetAutoIncrementCounters(User.class);
    }

    @Test
    void testUserCreation() {
        User user = new User("Kotka", "kotka_1211@example.com");

        assertEquals(1, user.getId());
        assertEquals("Kotka", user.getName());
        assertEquals("kotka_1211@example.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User("Elena", "Elena@example.com");
        user.setName("Elena Mudraya");
        user.setEmail("Mudraya@example.com");

        assertEquals("Elena Mudraya", user.getName());
        assertEquals("Mudraya@example.com", user.getEmail());
    }

    @Test
    void testAddLoan() {
        User user = new User("Elena", "Elena@example.com");
        Loan loan1 = new Loan(1, user.getId(), LocalDate.now());

        user.addLoan(loan1);
        List<Loan> loans = user.getCurrentLoans();

        assertEquals(1, loans.size());
        assertTrue(loans.contains(loan1));
    }

    @Test
    void testAddLoanMultipleLoans() {
        User user = new User("Elena", "Elena@example.com");
        Loan loan1 = new Loan(1, user.getId(), LocalDate.now());
        Loan loan2 = new Loan(2, user.getId(), LocalDate.now());

        user.addLoan(loan1);
        user.addLoan(loan2);
        List<Loan> loans = user.getCurrentLoans();

        assertEquals(2, loans.size());
        assertTrue(loans.contains(loan1));
        assertTrue(loans.contains(loan2));
    }

    @Test
    void testRemoveLoan() {
        User user = new User("Elena", "Elena@example.com");
        Loan loan1 = new Loan(1, user.getId(), LocalDate.now());
        Loan loan2 = new Loan(2, user.getId(), LocalDate.now());

        user.addLoan(loan1);
        user.addLoan(loan2);

        user.removeLoan(loan1);
        List<Loan> loans = user.getCurrentLoans();

        assertEquals(1, loans.size());
        assertFalse(loans.contains(loan1));
        assertTrue(loans.contains(loan2));
    }
}