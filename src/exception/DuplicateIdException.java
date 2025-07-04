package exception;

public class DuplicateIdException extends Exception {
    public DuplicateIdException(String entityType, int id) {
        super(String.format("%s с ID %d уже существует", entityType, id));
    }
}