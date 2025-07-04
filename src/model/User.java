package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private static int lastId = 0;
    private final int id;
    private String name;
    private String email;
    private List<Loan> currentLoans;

    public User(String name, String email) {
        this.id = ++lastId;
        this.name = name;
        this.email = email;
        this.currentLoans = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getCurrentLoans() {
        return currentLoans;
    }

    public void addLoan(Loan loan) {
        currentLoans.add(loan);
    }

    public void removeLoan(Loan loan) {
        currentLoans.remove(loan);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}