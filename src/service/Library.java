package service;

import exception.DuplicateIdException;
import model.Book;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private static final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9]+";

    private final Map<Integer, Book> books;
    private final Map<Integer, User> users;

    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
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
}