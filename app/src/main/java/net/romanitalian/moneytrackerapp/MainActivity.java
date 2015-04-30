package net.romanitalian.moneytrackerapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private TransactionAdapter transactionAdapter;
    List<Transactions> data = new ArrayList<>();
    String date_format = "yyyy-MM-dd";

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
        DateFormat df = new SimpleDateFormat(date_format, new Locale("ru"));
        Date now_calendar = Calendar.getInstance().getTime();
        String now = df.format(now_calendar);
        data.add(new Transactions("Huawei", "9800", now));
        data.add(new Transactions("SamsungS3", "13000", now));
        data.add(new Transactions("T-shirt", "300", now));
        data.add(new Transactions("Jeans", "1500", now));
        data.add(new Transactions("Printer", "4500", now));
        return data;
    }
}
