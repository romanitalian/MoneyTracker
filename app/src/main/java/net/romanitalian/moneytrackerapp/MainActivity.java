package net.romanitalian.moneytrackerapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private TransactionAdapter transactionAdapter;
    List<Transactions> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Transactions> adapterData = getDataList();
        transactionAdapter = new TransactionAdapter(this, adapterData);

        listView = (ListView) findViewById(R.id.list_view_id);
        listView.setAdapter(transactionAdapter);
    }

    private List<Transactions> getDataList() {
        data.add(new Transactions("Huawei", "9800"));
        data.add(new Transactions("SamsungS3", "13000"));
        data.add(new Transactions("T-shirt", "300"));
        data.add(new Transactions("Jeans", "1500"));
        data.add(new Transactions("Printer", "4500"));
        return data;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
