package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.widget.LinearLayout;

import net.romanitalian.moneytrackerapp.PieChartView;
import net.romanitalian.moneytrackerapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {

    @ViewById
    LinearLayout statistic_linear_layout;

    @ViewById(R.id.piecahrt)
    PieChartView pieChartView;

    float[] datapoints = {450, 1290, 300, 500};

    @AfterViews
    void ready() {
//        statistic.setText(R.string.statistics_text);
        pieChartView.setDatapoints(datapoints);
    }
}
