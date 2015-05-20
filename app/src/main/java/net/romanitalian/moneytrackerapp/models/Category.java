package net.romanitalian.moneytrackerapp.models;

public class Category {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category(String title) {
        this.title = title;
    }
}
