package net.romanitalian.moneytrackerapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.utils.Udate;
import net.romanitalian.moneytrackerapp.utils.Uerror;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_transaction)
public class AddTransactionActivity extends ActionBarActivity {
    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText title;

    @ViewById
    EditText sum;

    @ViewById
    Button add_transaction;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.add_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Transaction transactionLast = getTransactionLast();
        if (transactionLast != null && transactionLast.isValid()) {
            title.setText(transactionLast.comment);
            sum.setText(String.valueOf(transactionLast.sum));
        }
    }

    @Click
    void AddTransaction() {
        if (isValidForm()) {
            new Transaction(title.getText().toString(), sum.getText().toString(), Udate.getDateNow()).save();
            finish();
        } else {
            Uerror.showAlert(this);
        }
    }

    @TextChange
    void titleTextChanged(CharSequence text) {
        add_transaction.setEnabled(!TextUtils.isEmpty(text));
    }

    @OptionsItem
    void homeSelected() {
        onBackPressed();
    }

    public Transaction getTransactionLast() {
        return new Select()
                .from(Transaction.class)
                .orderBy("date DESC")
                .executeSingle();
    }

    boolean isValidForm() {
        return title.getText().length() != 0 && sum.getText().length() != 0;
    }
}
