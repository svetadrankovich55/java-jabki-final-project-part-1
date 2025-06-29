package test;

import exception.DuplicateIdException;
import exception.BookNotFoundException;
import exception.UserNotFoundException;
import exception.UsersNotFoundException;
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
        BookNotFoundException exception = new BookNotFoundException("котиков");
        assertEquals("По данному запросу котиков не найдено", exception.getMessage());
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
}