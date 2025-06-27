import exception.DuplicateIdException;
import model.Book;
import model.User;
import service.Library;
import ui.ConsoleMenu;


public class Main {
    public static void main(String[] args) throws DuplicateIdException {
        Library library = new Library();
        initializeSampleData(library);
        ConsoleMenu consoleMenu = new ConsoleMenu(library);
        consoleMenu.start();
    }

    private static void initializeSampleData(Library library) throws DuplicateIdException {
        library.addBook(new Book(1, "1984", "George Orwell", 1949, 5, 3));
        library.addBook(new Book(2, "The Hobbit", "J.R.R. Tolkien", 1937, 3, 1));
        library.addBook(new Book(3, "Dune", "Frank Herbert", 1965, 4, 4));
        library.addUser(new User(1, "Alice", "alice@example.com"));
        library.addUser(new User(2, "Bob", "bob@example.com"));
    }
}