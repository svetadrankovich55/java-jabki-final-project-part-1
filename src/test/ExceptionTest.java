package test;

import exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionTest {

    @Test
    void testDuplicateIdException() {
        DuplicateIdException exception = new DuplicateIdException("Книга", 1);
        assertEquals("Книга с ID 1 уже существует", exception.getMessage());
    }

    @Test
    void testObjectNotFoundException() {
        BookNotFoundException exception = new BookNotFoundException();
        assertEquals("По данному запросу книг не найдено", exception.getMessage());
    }

    @Test
    void testUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException(456);
        assertEquals("Пользователь с ID 456 не найден", exception.getMessage());
    }

    @Test
    void testUsersNotFoundException() {
        UsersNotFoundException exception = new UsersNotFoundException("пользователи");
        assertEquals("По данному запросу пользователи не найдены", exception.getMessage());
    }

    @Test
    void testBookNotAvailableException() {
        BookNotAvailableException exception = new BookNotAvailableException();
        assertEquals("Нет доступных копий для этой книги", exception.getMessage());
    }

    @Test
    void testBookNotLoanedException() {
        BookNotLoanedException exception = new BookNotLoanedException();
        assertEquals("Эта книга не была выдана этому пользователю", exception.getMessage());
    }

    @Test
    void testLoansNotFoundException() {
        LoansNotFoundException exception = new LoansNotFoundException();
        assertEquals("По заданным критериям выдачи не найдены.", exception.getMessage());
    }
    @Test
    void testOverdueLoansNotFoundException() {
        OverdueLoansNotFoundException exception = new OverdueLoansNotFoundException();
        assertEquals("Нет просроченных выдач.", exception.getMessage());
    }

    @Test
    void testUserLoanLimitReachedException() {
        UserLoanLimitReachedException exception = new UserLoanLimitReachedException(4);
        assertEquals("У пользователя уже максимальное количество книг 4", exception.getMessage());
    }
}