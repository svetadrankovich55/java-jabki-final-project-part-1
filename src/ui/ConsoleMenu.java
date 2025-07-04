package ui;

import exception.BookNotFoundException;
import exception.DuplicateIdException;
import exception.UserNotFoundException;
import exception.UsersNotFoundException;
import exception.OverdueLoansNotFoundException;
import exception.LoansNotFoundException;

import model.Book;
import model.Loan;
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
                        searchBooksByTitleAndAuthorAndYear();
                        break;
                    case "4":
                        addUser();
                        break;
                    case "5":
                        showAllUsers();
                        break;
                    case "6":
                        searchUserById();
                        break;
                    case "7":
                        loanBook();
                        break;
                    case "8":
                        returnBook();
                        break;
                    case "9":
                        loansByUserByBookReport();
                        break;
                    case "10":
                        overdueLoansReport();
                        break;
                    case "11":
                        System.out.println("Выход из программы...");
                        return;
                    default:
                        System.out.println("Неверный ввод. Пожалуйста, выберите пункт меню от 1 до 11.");
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
        System.out.println("3. Поиск книг по названию, автору, году издания");
        System.out.println("4. Добавление пользователя");
        System.out.println("5. Просмотр всех пользователей");
        System.out.println("6. Поиск пользователя по ID");
        System.out.println("7. Выдача книги ");
        System.out.println("8. Возврат книги");
        System.out.println("9. Просмотр истории выдач по конкретному пользователю, по конкретной книге");
        System.out.println("10. Поиск просроченных выдач");
        System.out.println("11. Выход");
        System.out.print("Выберите действие: ");
    }

    private void addBook() {
        System.out.println("\nДобавление новой книги:");
        String title = getStringInput("Название: ");
        String author = getStringInput("Автор: ");
        int year = getIntInput("Год издания: ");
        int totalCopies = getIntInput("Общее количество копий: ");
        int availableCopies = getIntInput("Количество доступных копий: ");

        try {
            library.addBook(new Book(title, author, year, totalCopies, availableCopies));
            System.out.println("Книга успешно добавлена!");
        } catch (DuplicateIdException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllBooks() throws BookNotFoundException {
        System.out.println("\nСписок всех книг:");
        List<Book> books = library.getAllBooks();

        if (books.isEmpty()) {
            throw new BookNotFoundException();
        } else {
            printBooksInfo(books);
        }
    }

    private void searchBooksByTitleAndAuthorAndYear() throws BookNotFoundException {
        System.out.println("\nПоиск книг (введите 0 для пропуска поля)");

        String title = getOptionalStringInput("Введите название книги: ");
        String author = getOptionalStringInput("Введите автора: ");
        int year = getOptionalIntInput("Введите год издания: ");
        String processedTitle;
        if ("0".equals(title)) {
            processedTitle = null;
        } else {
            processedTitle = title;
        }

        String processedAuthor;
        if ("0".equals(author)) {
            processedAuthor = null;
        } else {
            processedAuthor = author;
        }

        Integer processedYear;
        if (0 == year) {
            processedYear = null;
        } else {
            processedYear = year;
        }

        List<Book> books = library.searchBooksByTitleAndAuthorAndYear(processedTitle, processedAuthor, processedYear);

        if (books.isEmpty()) {
            throw new BookNotFoundException();
        } else {
            printSearchResults(books);
        }
    }

    private void addUser() {
        System.out.println("\nДобавление нового пользователя:");
        String name = getStringInput("Имя: ");
        String email = getStringInput("Email: ");

        try {
            library.addUser(new User(name, email));
            System.out.println("Пользователь успешно добавлен!");
        } catch (DuplicateIdException | IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllUsers() throws UsersNotFoundException {
        System.out.println("\nСписок всех пользователей:");
        List<User> users = library.getAllUsers();
        if (users.isEmpty()) {
            throw new UsersNotFoundException("пользователи");
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

    private void loanBook() {
        System.out.println("\nОформление выдачи книги:");
        int bookId = getIntInput("Введите ID книги: ");
        int userId = getIntInput("Введите ID пользователя: ");
        try {
            library.loanBook(bookId, userId);
            System.out.println("Книга успешно выдана!");
        } catch (Exception e) {
            System.out.println("Ошибка при выдаче книги: " + e.getMessage());
        }
    }

    private void returnBook() {
        System.out.println("\nОформление возврата книги:");
        int bookId = getIntInput("Введите ID книги: ");
        int userId = getIntInput("Введите ID пользователя: ");
        try {
            library.returnBook(bookId, userId);
            System.out.println("Книга успешно возвращена!");
        } catch (Exception e) {
            System.out.println("Ошибка при возврате книги: " + e.getMessage());
        }
    }

    private void loansByUserByBookReport() {
        System.out.println("\nПросмотр истории выдач (введите 0 для пропуска поля)");

        int userId = getOptionalIntInput("Введите ID пользователя: ");
        int bookId = getOptionalIntInput("Введите ID книги: ");

        Integer processedUserId;
        if (0 == userId) {
            processedUserId = null;
        } else {
            processedUserId = userId;
        }
        Integer processedBookId;
        if (0 == bookId) {
            processedBookId = null;
        } else {
            processedBookId = bookId;
        }
        List<Loan> loans = library.getLoansByUserByBook(processedUserId, processedBookId);

        if (loans.isEmpty()) {
            throw new LoansNotFoundException();
        } else {
            System.out.println("Найдено " + loans.size() + " записей:");
            printLoans(loans);
        }
    }

    private void overdueLoansReport() {
        System.out.println("\nПросроченные выдачи");
        List<Loan> loans = library.getOverdueLoans();
        if (loans.isEmpty()) {
            throw new OverdueLoansNotFoundException();
        } else {
            System.out.println("Просроченные выдачи (более 30 дней):");
            printLoans(loans);
        }
    }

    private void printSearchResults(List<Book> books) throws BookNotFoundException {
        System.out.println("\nРезультаты поиска книг по заданным критериям:");
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        } else {
            printBooksInfo(books);
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

    private void printBooksInfo(List<Book> books) {
        for (Book book : books) {
            printBookInfo(book);
        }
    }

    private void printUserInfo(User user) {
        System.out.println("ID: " + user.getId() +
                ", Имя: " + user.getName() +
                ", Email: " + user.getEmail());
    }

    private void printLoanInfo(Loan loan) {
        System.out.println("ID книги: " + loan.getBookId() +
                ", ID пользователя: " + loan.getUserId() +
                ", Дата выдачи книги: " + loan.getLoanDate() +
                ", Дата возврата книги: " + loan.getReturnDate());
    }

    private void printLoans(List<Loan> loans) {
        for (Loan loan : loans) {
            printLoanInfo(loan);
        }
    }

    private String getStringInput(String str) {
        System.out.print(str);
        return scanner.nextLine().trim();
    }

    private String getOptionalStringInput(String str) {
        System.out.print(str);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return "0";
        } else {
            return input;
        }
    }

    private Integer getOptionalIntInput(String str) {
        while (true) {
            try {
                System.out.print(str);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return 0;
                } else {
                    return Integer.parseInt(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private int getIntInput(String str) {
        while (true) {
            try {
                System.out.print(str);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }
}