package test;

import exception.DuplicateIdException;
import model.Book;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Library;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() throws DuplicateIdException {
        library = new Library();
        book1 = new Book(1, "Война и мир", "Толстой Л.Н.", 1867, 5000000, 3000000);
        book2 = new Book(2, "Преступление и наказание", "Достоевский Ф.М.", 1866, 3000000, 1000000);
        user1 = new User(1, "Alice", "alice@example.com");
        user2 = new User(2, "Bob", "bob@example.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addUser(user1);
    }

    @Test
    void testAddBook() throws DuplicateIdException {
        Book newBook = new Book(3, "New Book", "Author", 2022, 2, 2);
        library.addBook(newBook);

        assertEquals(3, library.getAllBooks().size());
    }

    @Test
    void testAddDuplicateBook() {
        assertThrows(DuplicateIdException.class, () -> library.addBook(book1));
    }

    @Test
    void testGetAllBooks() {
        assertEquals(2, library.getAllBooks().size());
    }

    @Test
    void testSearchBooksByTitle() {
        List<Book> results = library.searchBooksByTitle("Война и мир");

        assertEquals(1, results.size());
        assertEquals("Толстой Л.Н.", results.get(0).getAuthor());
    }

    @Test
    void testSearchBooksByYear() throws DuplicateIdException {
        Book newBook = new Book(3, "Великий Гэтсби", "Фрэнсиса Скотта Фицджеральда", 1925, 2, 2);
        library.addBook(newBook);
        Book newBook1 = new Book(4, "Тихий Дон", "Шолохов М.А.", 1925, 2, 2);
        library.addBook(newBook1);

        List<Book> results = library.searchBooksByYear(1925);

        assertEquals(2, results.size());
        assertEquals("Великий Гэтсби", results.get(0).getTitle());
    }

    @Test
    void testSearchBooksByTitleAndAuthor() {
        List<Book> results = library.searchBooksByTitleAndAuthor("Война и мир", "Толстой Л.Н.");

        assertEquals(1, results.size());
        assertEquals("Война и мир", results.get(0).getTitle());
    }

    @Test
    void testSearchBooksByAuthorAndYear() throws DuplicateIdException {
        Book newBook = new Book(3, "Великий Гэтсби", "Фрэнсиса Скотта Фицджеральда", 1925, 2, 2);
        library.addBook(newBook);
        Book newBook1 = new Book(4, "Тихий Дон", "Шолохов М.А.", 1925, 2, 2);
        library.addBook(newBook1);

        List<Book> results = library.searchBooksByAuthorAndYear("Шолохов М.А.", 1925);

        assertEquals(1, results.size());
        assertEquals("Тихий Дон", results.get(0).getTitle());
    }

    @Test
    void testSearchBooksByTitleAndAuthorAndYear() throws DuplicateIdException {
        Book newBook = new Book(3, "Великий Гэтсби", "Фрэнсиса Скотта Фицджеральда", 1925, 2, 2);
        library.addBook(newBook);
        Book newBook1 = new Book(4, "Тихий Дон", "Шолохов М.А.", 1925, 2, 2);
        library.addBook(newBook1);

        List<Book> results = library.searchBooksByTitleAndAuthorAndYear("Великий Гэтсби", "Фрэнсиса Скотта Фицджеральда", 1925);

        assertEquals(1, results.size());
        assertEquals("Великий Гэтсби", results.get(0).getTitle());
    }

    @Test
    void testAddUser() throws DuplicateIdException {
        library.addUser(user2);

        assertEquals(2, library.getAllUsers().size());
    }

    @Test
    void testAddUserWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> library.addUser(new User(3, "Invalid", "no-email")));
    }

    @Test
    void testGetUserById() {
        User foundUser = library.getUserById(1);
        assertEquals("Alice", foundUser.getName());
    }

    @Test
    void testGetNonExistentUser() {
        assertNull(library.getUserById(999));
    }
}