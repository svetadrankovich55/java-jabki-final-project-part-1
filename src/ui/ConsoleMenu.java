package ui;

import exception.DuplicateIdException;
import exception.ObjectNotFoundException;
import exception.UserNotFoundException;
import model.Book;
import model.User;
import service.Library;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Library library;
    private final Scanner scanner;

    public ConsoleMenu(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        showAllBooks();
                        break;
                    case "3":
                        searchBooksByTitle();
                        break;
                    case "4":
                        searchBooksByAuthor();
                        break;
                    case "5":
                        searchBooksByYear();
                        break;
                    case "6":
                        searchBooksByTitleAndAuthor();
                        break;
                    case "7":
                        searchBooksByAuthorAndYear();
                        break;
                    case "8":
                        searchBooksByTitleAndAuthorAndYear();
                        break;
                    case "9":
                        addUser();
                        break;
                    case "10":
                        showAllUsers();
                        break;
                    case "11":
                        searchUserById();
                        break;
                    case "12":
                        System.out.println("Выход из программы...");
                        return;
                    default:
                        System.out.println("Неверный ввод. Пожалуйста, выберите пункт меню от 1 до 12.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Добавление книги");
        System.out.println("2. Просмотр всех книг");
        System.out.println("3. Поиск книг по названию");
        System.out.println("4. Поиск книг по автору");
        System.out.println("5. Поиск книг по году издания");
        System.out.println("6. Поиск книг по названию и автору");
        System.out.println("7. Поиск книг по автору и году издания");
        System.out.println("8. Поиск книг по названию, автору, году издания");
        System.out.println("9. Добавление пользователя");
        System.out.println("10. Просмотр всех пользователей");
        System.out.println("11. Поиск пользователя по ID");
        System.out.println("12. Выход");
        System.out.print("Выберите действие: ");
    }

    private void addBook() {
        System.out.println("\nДобавление новой книги:");
        int id = getIntInput("ID: ");
        String title = getStringInput("Название: ");
        String author = getStringInput("Автор: ");
        int year = getIntInput("Год издания: ");
        int totalCopies = getIntInput("Общее количество копий: ");
        int availableCopies = getIntInput("Количество доступных копий: ");

        try {
            library.addBook(new Book(id, title, author, year, totalCopies, availableCopies));
            System.out.println("Книга успешно добавлена!");
        } catch (DuplicateIdException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllBooks() throws ObjectNotFoundException {
        System.out.println("\nСписок всех книг:");
        List<Book> books = library.getAllBooks();

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            for (Book book : books) {
                printBookInfo(book);
            }
        }
    }

    private void searchBooksByTitle() throws ObjectNotFoundException {
        String title = getStringInput("Введите название книги для поиска: ");
        List<Book> books = library.searchBooksByTitle(title);

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            printSearchResults(books, "книг по названию");
        }
    }

    private void searchBooksByAuthor() throws ObjectNotFoundException {
        String author = getStringInput("Введите автора книги для поиска: ");
        List<Book> books = library.searchBooksByAuthor(author);

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            printSearchResults(books, "книг по автору");
        }
    }

    private void searchBooksByYear() throws ObjectNotFoundException {
        int year = getIntInput("Введите год издания для поиска: ");
        List<Book> books = library.searchBooksByYear(year);

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            printSearchResults(books, "книг по году издания");
        }
    }

    private void searchBooksByTitleAndAuthor() throws ObjectNotFoundException {
        String title = getStringInput("Введите название книги: ");
        String author = getStringInput("Введите автора: ");
        List<Book> books = library.searchBooksByTitleAndAuthor(title, author);

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            printSearchResults(books, "книг по названию и автору");
        }
    }

    private void searchBooksByAuthorAndYear() throws ObjectNotFoundException {
        String author = getStringInput("Введите автора: ");
        int year = getIntInput("Введите год издания: ");
        List<Book> books = library.searchBooksByAuthorAndYear(author, year);

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            printSearchResults(books, "книг по автору и году издания");
        }
    }

    private void searchBooksByTitleAndAuthorAndYear() throws ObjectNotFoundException {
        String title = getStringInput("Введите название книги: ");
        String author = getStringInput("Введите автора: ");
        int year = getIntInput("Введите год издания: ");
        List<Book> books = library.searchBooksByTitleAndAuthorAndYear(title, author, year);

        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            printSearchResults(books, "книг по названию, автору и году издания");
        }
    }

    private void addUser() {
        System.out.println("\nДобавление нового пользователя:");
        int id = getIntInput("ID пользователя: ");
        String name = getStringInput("Имя: ");
        String email = getStringInput("Email: ");

        try {
            library.addUser(new User(id, name, email));
            System.out.println("Пользователь успешно добавлен!");
        } catch (DuplicateIdException | IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllUsers() throws ObjectNotFoundException {
        System.out.println("\nСписок всех пользователей:");
        List<User> users = library.getAllUsers();
        if (users.isEmpty()) {
            throw new ObjectNotFoundException("пользователей");
        } else {
            for (User user : users) {
                printUserInfo(user);
            }
        }
    }

    private void searchUserById() throws UserNotFoundException {
        int id = getIntInput("Введите ID пользователя: ");
        User user = library.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        } else {
            printUserInfo(user);
        }
    }

    private void printSearchResults(List<Book> books, String searchType) throws ObjectNotFoundException {
        System.out.println("\nРезультаты поиска " + searchType + ":");
        if (books.isEmpty()) {
            throw new ObjectNotFoundException("книг");
        } else {
            for (Book book : books) {
                printBookInfo(book);
            }
        }
    }

    private void printBookInfo(Book book) {
        System.out.println("ID: " + book.getId() +
                ", Название: " + book.getTitle() +
                ", Автор: " + book.getAuthor() +
                ", Год: " + book.getYear() +
                ", Всего копий: " + book.getTotalCopies() +
                ", Доступно: " + book.getAvailableCopies());
    }

    private void printUserInfo(User user) {
        System.out.println("ID: " + user.getId() +
                ", Имя: " + user.getName() +
                ", Email: " + user.getEmail());
    }

    private String getStringInput(String str) {
        System.out.print(str);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }
}