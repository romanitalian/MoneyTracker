package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activeandroid.query.Select;
import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.activities.AddTransactionActivity_;
import net.romanitalian.moneytrackerapp.adapters.TransactionAdapter;
import net.romanitalian.moneytrackerapp.models.Transaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Click
    void fabClicked() {
        AddTransactionActivity_.intent(getActivity()).start();
    }

    private List<Transaction> getTransactions() {
        data = new Select()
                .from(Transaction.class)
                .orderBy("date DESC")
                .execute();
        return data;
    }
}

