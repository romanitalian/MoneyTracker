package net.romanitalian.moneytrackerapp.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.adapters.CategoryAdapter;
import net.romanitalian.moneytrackerapp.models.Category;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_categories)
@OptionsMenu(R.menu.categories_menu)
public class CategoriesFragment extends Fragment {
    private CategoryAdapter categoryAdapter;
    private static final String FILTER_TIMER = "filter_timer";
    List<Category> data = new ArrayList<>();

    @ViewById
    RecyclerView categoryList;

    @ViewById
    FloatingActionButton fab;

    @OptionsMenuItem
    MenuItem menuSearch;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @AfterViews
    void ready() {
        List<Category> categories = Category.getAll("");
        categoryAdapter = new CategoryAdapter(categories);
        categoryList.setAdapter(categoryAdapter);

        categoryList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryList.setLayoutManager(linearLayoutManager);

        categoryList.setAdapter(categoryAdapter);
        fab.attachToRecyclerView(categoryList);

        swipeRefreshLayout.setColorSchemeColors(R.color.green, R.color.orange, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCategories("");
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                categoryAdapter.removeItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(categoryList);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories("");
    }

    @Click
    void fabClicked() {
//        AddCategoryActivity_.intent(getActivity()).start();
        alertAddCcategory();
    }

    public void alertAddCcategory() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_window);
        final TextView textView = (TextView) dialog.findViewById(R.id.title);
        final EditText editText = (EditText) dialog.findViewById(R.id.edit_text);
        Button okButton = (Button) dialog.findViewById(R.id.ok_button);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);

        textView.setText(getString(R.string.categories_text));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable text = editText.getText();
                if (!TextUtils.isEmpty(text)) {
                    new Category(text.toString()).save();
                    dialog.dismiss();
                    loadCategories("");
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Background(delay = 300, id = FILTER_TIMER)
    void filterDelayed(String filter) {
        loadCategories(filter);
    }

    private void loadCategories(final String filter) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Category>>() {
            @Override
            public Loader<List<Category>> onCreateLoader(int i, Bundle bundle) {
                final AsyncTaskLoader<List<Category>> loader = new AsyncTaskLoader<List<Category>>(getActivity()) {
                    @Override
                    public List<Category> loadInBackground() {
                        return Category.getAll(filter);
                    }
                };
                loader.forceLoad();
                return loader;
            }
            @Override
            public void onLoadFinished(Loader<List<Category>> loader, List<Category> categories) {
                swipeRefreshLayout.setRefreshing(false);
                categoryAdapter = new CategoryAdapter(categories);
                categoryList.setAdapter(categoryAdapter);
            }
            @Override
            public void onLoaderReset(Loader<List<Category>> loader) {
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
                loadCategories(newText);
                return true;
            }
        });
        searchView.setSearchableInfo(((SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE)).getSearchableInfo(getActivity().getComponentName()));
    }


}
