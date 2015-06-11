package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.activities.AddTransactionActivity_;
import net.romanitalian.moneytrackerapp.adapters.TransactionAdapter;
import net.romanitalian.moneytrackerapp.models.Transaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_transactions)
@OptionsMenu(R.menu.transactions_menu)
public class TransactionsFragment extends Fragment {
    private static final String FILTER_TIMER = "filter_timer";
    private TransactionAdapter transactionAdapter;

    List<Transaction> data = new ArrayList<>();

    @ViewById
    RecyclerView transaction_list;

    @ViewById
    FloatingActionButton fab;

    @OptionsMenuItem
    MenuItem menuSearch;

    @AfterViews
    void ready() {
        List<Transaction> adapterData = Transaction.getAll("");
        transactionAdapter = new TransactionAdapter(adapterData);

        transaction_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        transaction_list.setLayoutManager(linearLayoutManager);

        transaction_list.setAdapter(transactionAdapter);
        fab.attachToRecyclerView(transaction_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTransactions("");
    }

    @Click
    void fabClicked() {
        AddTransactionActivity_.intent(getActivity()).start();
    }

    @Background(delay = 300, id = FILTER_TIMER)
    void filterDelayed(String filter) {
        loadTransactions(filter);
    }

    private void loadTransactions(final String filter) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Transaction>>() {
            @Override
            public Loader<List<Transaction>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Transaction>> transactionLoader = new AsyncTaskLoader<List<Transaction>>(getActivity()) {
                    @Override
                    public List<Transaction> loadInBackground() {
                        return Transaction.getAll(filter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadTransactions(newText);
                return true;
            }
        });
        searchView.setSearchableInfo(((SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE)).getSearchableInfo(getActivity().getComponentName()));
    }

}

