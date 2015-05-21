package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.romanitalian.moneytrackerapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {

    @ViewById
    LinearLayout statistic_linear_layout;

    @ViewById
    TextView statistic;

    @AfterViews
    void ready() {
        statistic.setText(R.string.statistics_text);
    }
}
