package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import net.romanitalian.moneytrackerapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {

    @ViewById
    LinearLayout statistic_linear_layout;

    @ViewById(R.id.chart)
    PieChart chart;

    float[] datapoints = {450, 1290, 300, 500};

    @AfterViews
    void ready() {
//        statistic.setText(R.string.statistics_text);
//        pieChartView.setDatapoints(datapoints);

        chart = configureChart(chart);
        chart = setData(chart);
        chart.animateXY(1500, 1500);
    }


    public PieChart configureChart(PieChart chart) {
        chart.setHoleColor(getResources().getColor(android.R.color.background_dark));
        chart.setHoleRadius(120f);
        chart.setDescription("Статистика по категориям");
        chart.setTransparentCircleRadius(5f);
//        chart.setDrawYValues(true);
        chart.setDrawCenterText(true);
        chart.setDrawHoleEnabled(false);
        chart.setRotationAngle(0);
//        chart.setDrawXValues(false);
        chart.setRotationEnabled(true);
        chart.setUsePercentValues(true);
        chart.setCenterText("MoneyTracker");
        return chart;
    }

    private PieChart setData(PieChart chart) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(10, 0));
        yVals1.add(new Entry(2, 1));
        yVals1.add(new Entry(20, 2));
        yVals1.add(new Entry(15, 3));
        yVals1.add(new Entry(7, 4));
        yVals1.add(new Entry(17, 5));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Food");
        xVals.add("Electricity");
        xVals.add("Travel");
        xVals.add("Medical/Dental");
        xVals.add("Closing");
        xVals.add("Rent");
        PieDataSet set1 = new PieDataSet(yVals1, "sdfgs");
        set1.setSliceSpace(0f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(android.R.color.holo_green_light));
        colors.add(getResources().getColor(android.R.color.holo_red_light));
        colors.add(getResources().getColor(android.R.color.holo_blue_bright));
        colors.add(getResources().getColor(android.R.color.holo_orange_dark));
        colors.add(getResources().getColor(android.R.color.holo_blue_dark));
        colors.add(getResources().getColor(android.R.color.holo_red_dark));
        colors.add(getResources().getColor(android.R.color.holo_green_dark));
        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
        return chart;
    }
}
