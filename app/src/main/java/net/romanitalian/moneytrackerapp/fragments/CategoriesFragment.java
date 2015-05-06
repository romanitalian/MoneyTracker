package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.adapters.CategoryAdapter;
import net.romanitalian.moneytrackerapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private ListView listView;
    private CategoryAdapter categoryAdapter;
    List<Category> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_categories, container, false);

        List<Category> adapterData = getCategories();
        categoryAdapter = new CategoryAdapter(getActivity(), adapterData);
        listView = (ListView) inflate.findViewById(R.id.category_list_view_id);
        listView.setAdapter(categoryAdapter);

        return inflate;
    }

    private List<Category> getCategories() {
        data.add(new Category("Category_01"));
        data.add(new Category("Category_02"));
        data.add(new Category("Category_03"));
        return data;
    }
}
