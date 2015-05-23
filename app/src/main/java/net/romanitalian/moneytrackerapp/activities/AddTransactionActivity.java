package net.romanitalian.moneytrackerapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import net.romanitalian.moneytrackerapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_transaction)
public class AddTransactionActivity extends ActionBarActivity {
    @ViewById
    Toolbar toolbar;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.add_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OptionsItem
    void homeSelected() {
        onBackPressed();
    }
}

