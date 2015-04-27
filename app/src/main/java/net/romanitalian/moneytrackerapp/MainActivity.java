package net.romanitalian.moneytrackerapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    String date_format = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setBoundles();
    }

    public void setBoundles() {
        Bundle bundle = new Bundle();
        bundle.putString("date_format", this.date_format);

        TransactionsFragment fragment = new TransactionsFragment();
        fragment.setArguments(bundle);
    }
}
