package net.romanitalian.moneytrackerapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    String date_format = "dd-MM-yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #try_bundle: in activity
        setFragmentParams();
    }

    public void setFragmentParams() {
        Fragment fragment = new TransactionsFragment();
        Bundle bundle = new Bundle();

        bundle.putString("date_format", date_format);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_transactions_id, fragment).commit();
    }
}
