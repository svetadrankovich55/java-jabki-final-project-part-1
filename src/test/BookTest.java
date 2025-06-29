package test;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @BeforeEach
    void resetCounter() throws Exception {
        TestUtils.resetAutoIncrementCounters(Book.class);
    }

    @Test
    void testBookCreation() {
        Book book = new Book( "Title", "Author", 2025, 5, 3);

        assertEquals(1, book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals(2025, book.getYear());
        assertEquals(5, book.getTotalCopies());
        assertEquals(3, book.getAvailableCopies());
    }

    @Test
    void testSettersAndGetters() {
        Book book = new Book( "Мир и война", "Толстой", 1818, 5000000, 3000000);
        book.setTitle("Война и мир");
        book.setAuthor("Толстой Л.Н.");
        book.setYear(1867);

        assertEquals("Война и мир", book.getTitle());
        assertEquals("Толстой Л.Н.", book.getAuthor());
        assertEquals(1867, book.getYear());
    }
}