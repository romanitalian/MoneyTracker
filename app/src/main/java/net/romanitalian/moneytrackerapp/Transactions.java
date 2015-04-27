package net.romanitalian.moneytrackerapp;

/**
 * Created by romadin on 24.04.2015.
 */
public class Transactions {
    private String title;
    private int sum;
    private String date;

    public Transactions(String title, String sum) {
        this.title = title;
        this.sum = Integer.valueOf(sum);
    }

    public Transactions(String title, String sum, String date) {
        this.title = title;
        this.sum = Integer.valueOf(sum);
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }
}
