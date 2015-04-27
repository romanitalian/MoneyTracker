package net.romanitalian.moneytrackerapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    String date_format = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBoundles();
        setContentView(R.layout.activity_main);
    }

    public void setBoundles() {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date_format", date_format);
        fragment.setArguments(bundle);
    }
}
