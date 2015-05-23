package net.romanitalian.moneytrackerapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import net.romanitalian.moneytrackerapp.utils.Udate;

@Table(name = "Transactions")
public class Transaction extends Model {
    @Column(name = "title")
    public String title;

    @Column(name = "sum")
    public int sum;

    @Column(name = "date")
    private String date;

    public Transaction() {
    }

    public Transaction(String title, String sum) {
        this.title = title;
        this.sum = Integer.valueOf(sum);
        this.date = Udate.getDateNow();
    }

    public Transaction(String title, String sum, String date) {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "title='" + title + '\'' +
                ", sum=" + sum +
                ", date='" + date + '\'' +
                '}';
    }
}
