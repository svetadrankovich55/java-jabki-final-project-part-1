package service;

import exception.DuplicateIdException;
import exception.BookNotFoundException;
import exception.UserNotFoundException;
import exception.UserLoanLimitReachedException;
import exception.BookNotAvailableException;
import exception.BookNotLoanedException;

import model.Book;
import model.Loan;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private static final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9]+";
    private static final int AMOUNT_LOAN_BOOK = 3;

    private final Map<Integer, Book> books;
    private final Map<Integer, User> users;
    private final List<Loan> loans;

    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.loans = new ArrayList<>();
    }

    // Методы работы с книгами
    public void addBook(Book book) throws DuplicateIdException {
        if (books.containsKey(book.getId())) {
            throw new DuplicateIdException("Книга", book.getId());
        }
        if (book.getTotalCopies() < 0) {
            throw new IllegalArgumentException("Общее количество копий не может быть отрицательным");
        }
        if (book.getAvailableCopies() < 0) {
            throw new IllegalArgumentException("Доступное количество копий не может быть отрицательным");
        }
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Доступное количество копий не может превышать общее количество");
        }
        books.put(book.getId(), book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> searchBooksByTitleAndAuthorAndYear(String title, String author, Integer year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            boolean matches = true;

            if (title != null && !title.trim().isEmpty()) {
                matches = matches && book.getTitle().toLowerCase().contains(title.toLowerCase().trim());
            }
            if (author != null && !author.trim().isEmpty()) {
                matches = matches && book.getAuthor().toLowerCase().contains(author.toLowerCase().trim());
            }
            if (year != null) {
                matches = matches && year.equals(book.getYear());
            }
            if (matches) {
                result.add(book);
            }
        }
        return result;
    }

    // Методы работы с пользователями
    public void addUser(User user) throws DuplicateIdException {
        if (users.containsKey(user.getId())) {
            throw new DuplicateIdException("Пользователь", user.getId());
        }
        if (user.getEmail() == null || !user.getEmail().matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Некорректный email. Пример: user@example.com");
        }
        users.put(user.getId(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public void loanBook(int bookId, int userId) throws BookNotFoundException, UserNotFoundException, UserLoanLimitReachedException, BookNotAvailableException {
        User user = users.get(userId);
        Book book = books.get(bookId);
        Loan loan = new Loan(bookId, userId, LocalDate.now());

        if (book == null) {
            throw new BookNotFoundException();
        }
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException();
        }
        if (user.getCurrentLoans().size() >= AMOUNT_LOAN_BOOK) {
            throw new UserLoanLimitReachedException(AMOUNT_LOAN_BOOK);
        }

        loans.add(loan);
        user.addLoan(loan);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(int bookId, int userId) throws BookNotFoundException, UserNotFoundException, BookNotLoanedException {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (book == null) {
            throw new BookNotFoundException();
        }
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        Loan loanToReturn = null;
        for (Loan loan : user.getCurrentLoans()) {
            if (loan.getBookId() == bookId && loan.getReturnDate() == null) {
                loanToReturn = loan;
                break;
            }
        }
        if (loanToReturn == null) {
            throw new BookNotLoanedException();
        }

        loanToReturn.setReturnDate(LocalDate.now());
        user.removeLoan(loanToReturn);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }

    public List<Loan> getLoansByUserByBook(Integer userId, Integer bookId) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            boolean matches = true;
            if (userId != null) {
                matches = matches && loan.getUserId() == userId;
            }
            if (bookId != null) {
                matches = matches && loan.getBookId() == bookId;
            }
            if (matches) {
                result.add(loan);
            }
        }
        return result;
    }

    public List<Loan> getOverdueLoans() {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.isOverdue()) {
                result.add(loan);
            }
        }
        return result;
    }
}