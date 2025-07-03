package exception;

public class UserLoanLimitReachedException extends Exception{
    public UserLoanLimitReachedException(int maxCurrentLoan) {
        super("У пользователя уже максимальное количество книг " + maxCurrentLoan);
    }
}