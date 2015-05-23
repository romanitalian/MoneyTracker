package net.romanitalian.moneytrackerapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.fragments.CategoriesFragment_;
import net.romanitalian.moneytrackerapp.fragments.StatisticsFragment_;
import net.romanitalian.moneytrackerapp.fragments.TransactionsFragment_;
import net.romanitalian.moneytrackerapp.models.Transaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private String dateFormat = "dd-MM-yyyy";

    @ViewById
    Toolbar toolbar;

    @ViewById
    DrawerLayout drawerWidget;

    @ViewById
    ListView drawerList;

    private ActionBarDrawerToggle drawerToggle;

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        Drawer.Result result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.transactions_title),
                        new PrimaryDrawerItem().withName(R.string.categories_title),
                        new PrimaryDrawerItem().withName(R.string.statistics_title)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        setFragment(position);
                        view.setBackgroundResource(R.color.green);
                    }
                })
                .build();
        setFragment(0);
//        Transaction transaction =  new Transaction("Cool stuf", "900");
//        transaction.save();
//
//        String loc = getRandomTransaction().toString();
//        Toast.makeText(this, loc, Toast.LENGTH_LONG).show();
    }

    public Transaction getRandomTransaction() {
        return new Select()
                .from(Transaction.class)
                .orderBy("RANDOM()")
                .executeSingle();
    }

    public void setFragmentParams(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("dateFormat", dateFormat);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.drawer_layout_frame, fragment).commit();
    }

    public void setFragment(int position) {
        switch (position) {
            case 0:
                setTitle(getString(R.string.transactions_title));
                setFragmentParams(TransactionsFragment_.builder().build());
                break;
            case 1:
                setTitle(getString(R.string.categories_title));
                setFragmentParams(CategoriesFragment_.builder().build());
                break;
            case 2:
                setTitle(getString(R.string.statistics_title));
                setFragmentParams(StatisticsFragment_.builder().build());
                break;
        }
    }
}
