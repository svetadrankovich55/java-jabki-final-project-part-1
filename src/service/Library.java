package service;

import exception.DuplicateIdException;
import model.Book;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private Map<Integer, Book> books;
    private Map<Integer, User> users;

    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
    }

    // Методы работы с книгами
    public void addBook(Book book) throws DuplicateIdException {
        if (books.containsKey(book.getId())) {
            throw new DuplicateIdException("Книга", book.getId());
        }
        books.put(book.getId(), book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getYear() == year) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByTitleAndAuthor(String title, String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            boolean matchesTitle = book.getTitle().toLowerCase().contains(title.toLowerCase());
            boolean matchesAuthor = book.getAuthor().toLowerCase().contains(author.toLowerCase());
            if (matchesTitle && matchesAuthor) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByAuthorAndYear(String author, int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            boolean matchesAuthor = book.getAuthor().toLowerCase().contains(author.toLowerCase());
            boolean matchesYear = year == book.getYear();
            if (matchesAuthor && matchesYear) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByTitleAndAuthorAndYear(String title, String author, int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            boolean matchesTitle = book.getTitle().toLowerCase().contains(title.toLowerCase());
            boolean matchesAuthor = book.getAuthor().toLowerCase().contains(author.toLowerCase());
            boolean matchesYear = year == book.getYear();
            if (matchesTitle && matchesAuthor && matchesYear) {
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
        if (user.getEmail() == null || !user.getEmail().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9]+")) {
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