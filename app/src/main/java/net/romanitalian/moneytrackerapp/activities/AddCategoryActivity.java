package net.romanitalian.moneytrackerapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Category;
import net.romanitalian.moneytrackerapp.utils.Uerror;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_category)
public class AddCategoryActivity extends ActionBarActivity {
    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText title;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.add_category));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Click
    void addCategory() {
        if (isValidForm()) {
            new Category(title.getText().toString()).save();
            finish();
        } else {
            Uerror.showAlert(this);
        }
    }

    @OptionsItem
    void homeSelected() {
        onBackPressed();
    }

    boolean isValidForm() {
        return title.getText().length() != 0;
    }
}
