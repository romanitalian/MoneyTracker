package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.adapters.TransactionAdapter;
import net.romanitalian.moneytrackerapp.models.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoriesFragment extends Fragment {
    private ListView listView;
    private TransactionAdapter transactionAdapter;
    List<Transaction> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // #try_bundle: disable root for this fragment - set 3-rd param to: "false". It mean: attachToRoot = false;
        final View inflate = inflater.inflate(R.layout.fragment_transactions, container, false);

        List<Transaction> adapterData = getTransactions();
        transactionAdapter = new TransactionAdapter(getActivity(), adapterData);
        listView = (ListView) inflate.findViewById(R.id.list_view_id);
        listView.setAdapter(transactionAdapter);

        return inflate;
    }

    private List<Transaction> getTransactions(/*String format*/) {
        // #try_bundle: in fragment
        Bundle bundle = getArguments();
        String date_format = bundle.getString("date_format");

        DateFormat df = new SimpleDateFormat(date_format, new Locale("ru"));
        Date now_calendar = Calendar.getInstance().getTime();
        String now = df.format(now_calendar);
        data.add(new Transaction("Huawei", "9800", now));
        data.add(new Transaction("SamsungS3", "13000", now));
        data.add(new Transaction("T-shirt", "300", now));
        data.add(new Transaction("Jeans", "1500", now));
        data.add(new Transaction("Printer", "4500", now));
        return data;
    }
}
