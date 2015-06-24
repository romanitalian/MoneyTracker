package net.romanitalian.moneytrackerapp.models;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import net.romanitalian.moneytrackerapp.utils.Udate;

import java.util.Date;
import java.util.List;

@Table(name = "Transactions")
public class Transaction extends Model {
    private static final int ID_UNSYNCED = 0;
    private static final int ID_SYNCED = -1;

    @Column(name = "uuid")
    public int id;

    @Column(name = "title")
    public String comment;

    @Column(name = "sum")
    public int sum;

    @Column(name = "date")
    public Date trDate;

    public Transaction() {
    }

    public Transaction(String title, String sum) {
        this.comment = title;
        this.sum = Integer.valueOf(sum);
        this.trDate = Udate.getDateNow();
    }

    public Transaction(String title, String sum, Date date) {
        this.comment = title;
        this.sum = Integer.valueOf(sum);
        this.trDate = date;
    }

    public boolean isValid() {
        return comment.length() != 0 && sum > 0;
    }

    public static List<Transaction> getAll(String filter) {
        final From from = new Select()
                .from(Transaction.class)
                .orderBy("date DESC");
        if (!TextUtils.isEmpty(filter))
            from.where("title LIKE ?", "%" + filter + "%");
        return from.execute();
    }
//
//    public static void delete(int id) {
//        new Delete()
//                .from(Transaction.class)
//                .where("id = ?", id)
//                .execute();
//    }

    public void markSynced() {
        id = ID_SYNCED;
        save();
    }

    public boolean isInDatabase() {
        return new Select()
                .from(Transaction.class)
                .where("uuid = ?", id)
                .executeSingle() != null;
    }

    public static List<Transaction> getSynced() {
        return new Select()
                .from(Transaction.class)
                .where("uuid = ?", ID_SYNCED)
                .execute();
    }

    public static List<Transaction> getUnsynced() {
        return new Select()
                .from(Transaction.class)
                .where("uuid = ?", ID_UNSYNCED)
                .execute();
    }

    public static List<Transaction> getItem(int position) {
        return new Select()
                .from(Transaction.class)
                .where("id = ?", position)
                .execute();
    }
}
