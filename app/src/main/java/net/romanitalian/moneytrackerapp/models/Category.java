package net.romanitalian.moneytrackerapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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
}
