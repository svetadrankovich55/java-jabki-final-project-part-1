package exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(int id) {
        super("Пользователь с ID " + id + " не найден");
    }
}