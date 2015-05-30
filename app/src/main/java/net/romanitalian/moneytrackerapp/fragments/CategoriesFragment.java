package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.melnykov.fab.FloatingActionButton;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.activities.AddCategoryActivity_;
import net.romanitalian.moneytrackerapp.adapters.CategoryAdapter;
import net.romanitalian.moneytrackerapp.models.Category;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {
    private ListView listView;
    private CategoryAdapter categoryAdapter;
    List<Category> data = new ArrayList<>();

    @ViewById
    RecyclerView category_list;

    @ViewById
    FloatingActionButton fab2;

    @AfterViews
    void ready() {
        List<Category> categories = getCategories();
        categoryAdapter = new CategoryAdapter(categories);
        category_list.setAdapter(categoryAdapter);

        fab2.attachToRecyclerView(category_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Category>>() {
            @Override
            public Loader<List<Category>> onCreateLoader(int i, Bundle bundle) {
                final AsyncTaskLoader<List<Category>> loader = new AsyncTaskLoader<List<Category>>(getActivity()) {
                    @Override
                    public List<Category> loadInBackground() {
                        return getCategories();
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

    @Click
    void fab2Clicked() {
        AddCategoryActivity_.intent(getActivity()).start();
    }

    private List<Category> getCategories() {
//        data.add(new Category("Category_01"));
//        data.add(new Category("Category_02"));
//        data.add(new Category("Category_03"));
//        return data;
        data = new Select()
                .from(Category.class)
                .orderBy("title")
                .execute();
        return data;
    }
}
