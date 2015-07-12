package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.widget.LinearLayout;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Category;
import net.romanitalian.moneytrackerapp.models.Transaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.HashMap;
import java.util.List;
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
        List<Category> categories = Category.getAll("");
        List<Transaction> transactions = Transaction.getAll("");
        Map<String, Float> byCategory = new HashMap<>();
        int sum;
        for (Transaction tran : transactions) {
            String categoryTitle = categories.get(tran.category_id).title;
            if (byCategory.containsKey(categoryTitle)) {
                sum = byCategory.remove(categoryTitle).intValue();
                byCategory.put(categoryTitle, (float) tran.sum + sum);
            } else {
                byCategory.put(categoryTitle, (float) tran.sum);
            }
        }
        if (byCategory.size() != 0) {
            for (Map.Entry<String, Float> cat : byCategory.entrySet()) {
                map.put(cat.getKey(), cat.getValue());
            }
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
}
