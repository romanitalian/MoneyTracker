package net.romanitalian.moneytrackerapp.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Category;
import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.utils.Uerror;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_add_transaction)
public class AddTransactionActivity extends ActionBarActivity {
    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText title;

    @ViewById
    EditText sum;

    @ViewById
    Button addTransaction;

    @ViewById
    Button DateTimeTransaction;

    @ViewById
    Spinner spinnerCategoryList;

    Date date;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.add_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Category> categoryList = Category.getAll("");
        String[] items = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            items[i] = categoryList.get(i).title;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spinnerCategoryList.setAdapter(adapter);
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
        this.date = DatePickerFragment.date;
        if (isValidForm()) {
            DateTimeTransaction.getText();
            Transaction transaction = new Transaction(
                    title.getText().toString(),
                    sum.getText().toString(),
                    date,
                    spinnerCategoryList.getSelectedItemPosition()
            );
            transaction.save();
            finish();
        } else {
            Uerror.showAlert(this);
        }
    }

    @Click
    void DateTimeTransaction() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

//    @TextChange
//    void titleTextChanged(CharSequence text) {
//        addTransaction.setEnabled(!TextUtils.isEmpty(text));
//    }

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
        return title.getText().length() != 0
                && sum.getText().length() != 0
                && date != null
                && spinnerCategoryList.getSelectedItemPosition() > 0;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public static Date date;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            date = cal.getTime();

//            Locale locale = new Locale("ru");
//            DateFormat df = new SimpleDateFormat("yyyy.MM.dd", locale);
//            DateTimeTransaction.setText(df.format(date.getTime()));
        }
    }
}
