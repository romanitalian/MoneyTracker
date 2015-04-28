package net.romanitalian.moneytrackerapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    String date_format = "dd-MM-yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragmentParams();
        setContentView(R.layout.activity_main);
    }

    public void setFragmentParams() {
        // #try_bundle activity
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_transactions_id);
        Bundle bundle = new Bundle();
        bundle.putString("date_format", date_format);
        fragment.setArguments(bundle);
    }
}
