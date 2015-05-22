package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.activities.AddTransactionActivity;
import net.romanitalian.moneytrackerapp.adapters.TransactionAdapter;
import net.romanitalian.moneytrackerapp.models.Transaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@EFragment(R.layout.fragment_transactions)
public class TransactionsFragment extends Fragment {
    private TransactionAdapter transactionAdapter;
    List<Transaction> data = new ArrayList<>();

    @ViewById
    RecyclerView transaction_list;

    @ViewById
    FloatingActionButton fab;

    @AfterViews
    void ready() {
        List<Transaction> adapterData = getTransactions();
        transactionAdapter = new TransactionAdapter(adapterData);

        transaction_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        transaction_list.setLayoutManager(linearLayoutManager);

        transaction_list.setAdapter(transactionAdapter);
        fab.attachToRecyclerView(transaction_list);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTransactionActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private List<Transaction> getTransactions() {
        Bundle bundle = getArguments();
        String dateFormat = bundle.getString("dateFormat");

        DateFormat df = new SimpleDateFormat(dateFormat, new Locale("ru"));
        Date nowCalendar = Calendar.getInstance().getTime();
        String now = df.format(nowCalendar);
        data.add(new Transaction("Huawei", "9800", now));
        data.add(new Transaction("SamsungS3", "13000", now));
        data.add(new Transaction("T-shirt", "300", now));
        data.add(new Transaction("Jeans", "1500", now));
        data.add(new Transaction("Printer", "4500", now));
        data.add(new Transaction("Huawei", "9800", now));
        data.add(new Transaction("T-shirt", "300", now));
        data.add(new Transaction("Jeans", "1500", now));
        data.add(new Transaction("Printer", "4500", now));
        data.add(new Transaction("T-shirt", "300", now));
        data.add(new Transaction("Jeans", "1500", now));
        data.add(new Transaction("Printer", "4500", now));
        data.add(new Transaction("Huawei", "9800", now));
        data.add(new Transaction("T-shirt", "300", now));
        data.add(new Transaction("Jeans", "1500", now));
        data.add(new Transaction("Printer", "4500", now));
        return data;
    }
}

