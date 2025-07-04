package exception;

public class UsersNotFoundException extends Exception{
    public UsersNotFoundException(String entityType) {
        super("По данному запросу " + entityType + " не найдены");
    }
}