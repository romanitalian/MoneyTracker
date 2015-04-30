package net.romanitalian.moneytrackerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.adapters.StatisticAdapter;
import net.romanitalian.moneytrackerapp.models.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    private ListView listView;
    private StatisticAdapter categoryAdapter;
    List<Statistic> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_statistics, container, false);

        List<Statistic> adapterData = getCategories();
        categoryAdapter = new StatisticAdapter(getActivity(), adapterData);
        listView = (ListView) inflate.findViewById(R.id.statistics_id);
        listView.setAdapter(categoryAdapter);

        return inflate;
    }

    private List<Statistic> getCategories() {
        data.add(new Statistic("Statistic_01"));
        data.add(new Statistic("Statistic_02"));
        data.add(new Statistic("Statistic_03"));
        return data;
    }
}
