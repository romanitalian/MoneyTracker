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
import net.romanitalian.moneytrackerapp.activities.AddCategoryActivity_;
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
    RecyclerView category_list;

    @ViewById
    FloatingActionButton fab2;

    @OptionsMenuItem
    MenuItem menuSearch;

    @AfterViews
    void ready() {
        List<Category> categories = Category.getAll("");
        categoryAdapter = new CategoryAdapter(categories);
        category_list.setAdapter(categoryAdapter);

        category_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        category_list.setLayoutManager(linearLayoutManager);

        category_list.setAdapter(categoryAdapter);
        fab2.attachToRecyclerView(category_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories("");
    }

    @Click
    void fab2Clicked() {
        AddCategoryActivity_.intent(getActivity()).start();
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
                category_list.setAdapter(new CategoryAdapter(categories));
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
