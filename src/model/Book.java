package model;

public class Book {

    private int id;
    private String title;
    private String author;
    private int year;
    private int totalCopies;
    private int availableCopies;

    public Book(int id, String title, String author, int year, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Общее количество копий не может быть отрицательным");
        }
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0) {
            throw new IllegalArgumentException("Доступное количество копий не может быть отрицательным");
        }
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Доступное количество копий не может превышать общее количество");
        }
        this.availableCopies = availableCopies;
    }
}