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
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_categories, container, false);

        LinearLayout lt = (LinearLayout) inflate.findViewById(R.id.category_linear_layout);
        textView = (TextView) inflate.findViewById(R.id.category_id);
        lt.addView(textView);

        return inflate;
    }
}