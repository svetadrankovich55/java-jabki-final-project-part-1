package test;

import exception.*;
import model.Book;
import model.Loan;
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
    void setUp() throws Exception {
        TestUtils.resetAutoIncrementCounters(User.class, Book.class);

        library = new Library();
        book1 = new Book( "Война и мир", "Толстой Л.Н.", 1867, 5000000, 3000000);
        book2 = new Book( "Преступление и наказание", "Достоевский Ф.М.", 1866, 3000000, 1000000);
        user1 = new User( "Alice", "alice@example.com");
        user2 = new User( "Bob", "bob@example.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addUser(user1);
    }

    @Test
    void testAddBook() throws DuplicateIdException {
        Book newBook = new Book( "New Book", "Author", 2022, 2, 2);
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
    void testSearchBooksByTitleAndAuthorAndYear() throws DuplicateIdException {
        Book newBook = new Book( "Великий Гэтсби", "Фрэнсиса Скотта Фицджеральда", 1925, 2, 2);
        library.addBook(newBook);
        Book newBook1 = new Book( "Тихий Дон", "Шолохов М.А.", 1925, 2, 2);
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
        assertThrows(IllegalArgumentException.class, () -> library.addUser(new User( "Invalid", "no-email")));
    }

    @Test
    void testGetNonExistentUser() {
        assertNull(library.getUserById(999));
    }

    @Test
    void testGetAllUsers() {
        List<User> results =  library.getAllUsers();

        assertEquals(1, library.getAllUsers().size());
        assertEquals("Alice", results.get(0).getName());
    }
    
    @Test
    void testGetUserById() {
        User results =  library.getUserById(1);

        assertEquals("Alice", results.getName());
    }

    @Test
    void testLoanBook() throws Exception {
        library.loanBook(book1.getId(), user1.getId());

        assertEquals(1, user1.getCurrentLoans().size());
        assertEquals(book1.getId(), user1.getCurrentLoans().get(0).getBookId());
        assertEquals(2999999, book1.getAvailableCopies());
    }

    @Test
    void testReturnBook() throws Exception {
        library.loanBook(book2.getId(), user1.getId());
        library.returnBook(book2.getId(), user1.getId());

        assertTrue(user1.getCurrentLoans().isEmpty());
        assertEquals(1000000, book2.getAvailableCopies());

        List<Loan> loans = library.getLoansByUserByBook(user1.getId(), book2.getId());
        assertNotNull(loans.get(0).getReturnDate());
    }

    @Test
    void testGetLoansByUserByBook() throws Exception {
        library.loanBook(book2.getId(), user1.getId());

        // Поиск по пользователю
        List<Loan> userLoans = library.getLoansByUserByBook(user1.getId(), null);
        assertEquals(1, userLoans.size());
        assertEquals(user1.getId(), userLoans.get(0).getUserId());

        // Поиск по книге
        List<Loan> bookLoans = library.getLoansByUserByBook(null, book2.getId());
        assertEquals(1, bookLoans.size());
        assertEquals(book2.getId(), bookLoans.get(0).getBookId());

        // Поиск по обоим параметрам
        List<Loan> bothLoans = library.getLoansByUserByBook(user1.getId(), book2.getId());
        assertEquals(1, bothLoans.size());
    }
}