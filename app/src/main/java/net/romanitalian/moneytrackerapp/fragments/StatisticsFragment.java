package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.widget.LinearLayout;

import net.romanitalian.moneytrackerapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {

    @ViewById
    LinearLayout statistic_linear_layout;

    @ViewById(R.id.chart)
    PieChart chart;

    Map<String, Float> map = new HashMap<>();

    @AfterViews
    void ready() {
        map.put("Food", 15.7f);
        map.put("Closes", 35.0f);
        map.put("Electricity", 25.0f);
        map.put("Rent", 9.1f);
        Random r;
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();

            r = new Random();
            int color = Color.argb(100, r.nextInt(256), r.nextInt(256), r.nextInt(256));
            chart.addPieSlice(new PieModel(key, value, color));
        }
        chart.startAnimation();
    }
}
