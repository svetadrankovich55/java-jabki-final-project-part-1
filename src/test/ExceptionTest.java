package test;

import exception.DuplicateIdException;
import exception.ObjectNotFoundException;
import exception.UserNotFoundException;
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
        ObjectNotFoundException exception = new ObjectNotFoundException("котиков");
        assertEquals("По данному запросу котиков не найдено", exception.getMessage());
    }

    @Test
    void testUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException(456);
        assertEquals("Пользователь с ID 456 не найден", exception.getMessage());
    }
}