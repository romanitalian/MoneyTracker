package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.romanitalian.moneytrackerapp.R;

public class CategoriesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_categories, container, false);

        LinearLayout ll = (LinearLayout) inflate.findViewById(R.id.category_linear_layout);
        TextView text_view = (TextView) ll.findViewById(R.id.category_id);
        text_view.setText(R.string.categories_text);

        return inflate;
    }
}