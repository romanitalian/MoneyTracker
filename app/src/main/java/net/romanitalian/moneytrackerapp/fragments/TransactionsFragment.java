package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.activeandroid.query.Select;
import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.activities.AddTransactionActivity_;
import net.romanitalian.moneytrackerapp.adapters.TransactionAdapter;
import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.utils.Uinfo;

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


    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(this, "onResume run", Toast.LENGTH_LONG).show();
        Log.i(Uinfo.TAG, "onResume run");
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Transaction>>() {
            @Override
            public Loader<List<Transaction>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Transaction>> transactionLoader = new AsyncTaskLoader<List<Transaction>>(getActivity()) {
                    @Override
                    public List<Transaction> loadInBackground() {
                        return getTransactions();
                    }
                };
                transactionLoader.forceLoad();
                return transactionLoader;
            }

            @Override
            public void onLoadFinished(Loader<List<Transaction>> loader, List<Transaction> data) {
                transaction_list.setAdapter(new TransactionAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<Transaction>> loader) {

            }
        });
    }

    private List<Transaction> getTransactions() {
        data = new Select()
                .from(Transaction.class)
                .orderBy("date DESC")
                .execute();
        return data;
    }
}

