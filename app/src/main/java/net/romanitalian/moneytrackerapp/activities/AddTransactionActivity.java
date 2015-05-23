package net.romanitalian.moneytrackerapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.utils.Udate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_transaction)
public class AddTransactionActivity extends ActionBarActivity {
    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText title;

    @ViewById
    EditText sum;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.add_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Click
    void AddTransaction() {
        new Transaction(title.getText().toString(), sum.getText().toString(), Udate.getDateNow()).save();
        finish();
    }

    @OptionsItem
    void homeSelected() {
        onBackPressed();
    }
}

