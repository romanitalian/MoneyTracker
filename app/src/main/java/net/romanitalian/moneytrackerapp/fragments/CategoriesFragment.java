package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.widget.ListView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.adapters.CategoryAdapter;
import net.romanitalian.moneytrackerapp.models.Category;

import org.androidannotations.annotations.AfterViews;
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
    ListView category_list;

    @AfterViews
    void ready() {
        List<Category> adapterData = getCategories();
        categoryAdapter = new CategoryAdapter(getActivity(), adapterData);
        category_list.setAdapter(categoryAdapter);
    }

    private List<Category> getCategories() {
        data.add(new Category("Category_01"));
        data.add(new Category("Category_02"));
        data.add(new Category("Category_03"));
        return data;
    }
}
