package net.romanitalian.moneytrackerapp.models;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Category")
public class Category extends Model {
    @Column(name = "title")
    public String title;

    public Category(String title) {
        this.title = title;
    }

    /**
     * Just for annotasion.table
     */
    public Category() {}

    public static List<Category> getAll(String filter) {
        final From from = new Select()
                .from(Category.class)
                .orderBy("title");
        if (!TextUtils.isEmpty(filter))
            from.where("title LIKE ?", "%" + filter + "%");
        return from.execute();
    }
}
