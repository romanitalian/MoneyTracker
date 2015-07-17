package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.activities.AddTransactionActivity_;
import net.romanitalian.moneytrackerapp.adapters.TransactionAdapter;
import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.sync.SyncAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_transactions)
@OptionsMenu(R.menu.transactions_menu)
public class TransactionsFragment extends Fragment {
    private static final String FILTER_TIMER = "filter_timer";
    private TransactionAdapter transactionAdapter;
    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();

    List<Transaction> data = new ArrayList<>();

    @ViewById
    RecyclerView transactionList;

    @ViewById
    FloatingActionButton fab;

    @OptionsMenuItem
    MenuItem menuSearch;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @AfterViews
    void ready() {
        transactionList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        transactionList.setLayoutManager(linearLayoutManager);
        fab.attachToRecyclerView(transactionList);

        swipeRefreshLayout.setColorSchemeColors(R.color.green, R.color.orange, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTransactions("");
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                transactionAdapter.removeItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(transactionList);
    }

    @Receiver(actions = {SyncAdapter.SYNCED}, registerAt = Receiver.RegisterAt.OnResumeOnPause, local = true)
    void onSync() {
        loadTransactions("");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTransactions("");
    }

    @Click
    void fabClicked() {
        AddTransactionActivity_.intent(getActivity()).start();
        getActivity().overridePendingTransition(R.anim.from_midle, R.anim.to_midle);
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
                swipeRefreshLayout.setRefreshing(false);
                transactionAdapter = (new TransactionAdapter(data, getActivity(), new TransactionAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
                            actionMode = actionBarActivity.startSupportActionMode((actionModeCallback));
                        }
                        toggleSelection(position);
                        return true;
                    }
                }));
                transactionList.setAdapter(transactionAdapter);
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

    private void toggleSelection(int position) {
        transactionAdapter.toggleSelection(position);
        int count = transactionAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    transactionAdapter.removeItems(transactionAdapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
            transactionAdapter.clearSelection();
            actionMode = null;
        }
    }
}

